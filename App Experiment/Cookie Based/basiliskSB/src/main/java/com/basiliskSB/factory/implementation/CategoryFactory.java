package com.basiliskSB.factory.implementation;
import com.basiliskSB.dto.category.CategoryFilterDTO;
import com.basiliskSB.dto.category.CategoryGridDTO;
import com.basiliskSB.dto.category.UpsertCategoryDTO;
import com.basiliskSB.factory.abstraction.IndexFactory;
import com.basiliskSB.factory.abstraction.DeleteFactory;
import com.basiliskSB.factory.abstraction.UpsertFactory;
import com.basiliskSB.service.abstraction.CategoryService;
import com.basiliskSB.service.abstraction.CrudService;
import com.basiliskSB.utility.MapperHelper;
import com.basiliskSB.viewModel.implementation.category.CategoryDeleteViewModel;
import com.basiliskSB.viewModel.implementation.category.CategoryIndexViewModel;
import com.basiliskSB.viewModel.implementation.category.CategoryUpsertViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import java.util.HashMap;
import java.util.Map;

@Scope("singleton")
@Component
public class CategoryFactory implements IndexFactory, UpsertFactory, DeleteFactory {
    @Qualifier("categoryService")
    @Autowired
    private CrudService service;

    private Map<String, Object> urlParameters = new HashMap<>();
    private Integer page;

    private String getName(){
        return getUrlParameters().get("name").toString();
    }
    @Override
    public void setPage(Integer page) {
        this.page = page;
    }
    @Override
    public Integer getPage() {
        return page;
    }
    @Override
    public void setUrlParameters(String key, Object value) {
        urlParameters.put(key, value);
    }
    @Override
    public Map<String, Object> getUrlParameters() {
        return urlParameters;
    }
    @Override
    public void processIndex(Model model) {
        var pageCollection = service.getGrid(page, new CategoryFilterDTO(getName()));
        var grid = IndexFactory.getGridDTO(pageCollection, row -> new CategoryGridDTO(row));
        var viewModel = new CategoryIndexViewModel(getName())
            .setGrid(grid)
            .setPage(page)
            .setTotalPages(pageCollection.getTotalPages());
        viewModel.build(model);
    }
    private Long id;
    @Override
    public void setId(Object id) {
        this.id = (Long)id;
    }
    @Override
    public Object getId() {
        return id;
    }
    @Override
    public void processUpsertForm(Model model) {
        var viewModel = new CategoryUpsertViewModel();
        if (id != null) {
            var entity = service.getUpdate(id);
            var dto = new UpsertCategoryDTO(entity);
            viewModel.setBreadCrumbs("Category Index / Update Category")
                .setType("update")
                .setDto(dto);
        } else {
            var dto = new UpsertCategoryDTO();
            viewModel.setBreadCrumbs("Category Index / Insert Category")
                .setType("insert")
                .setDto(dto);
        }
        viewModel.build(model);
    }
    @Override
    public void processUpsertForm(Model model, Object dto) {
        var id = MapperHelper.getLongField(dto, "id");
        var viewModel = new CategoryUpsertViewModel();
        if (id != null) {
            viewModel.setBreadCrumbs("Category Index / Update Category")
                .setType("update")
                .setDto(dto);
        } else {
            viewModel.setBreadCrumbs("Category Index / Insert Category")
                .setType("insert")
                .setDto(dto);
        }
        viewModel.build(model);
    }

    @Override
    public void save(Object dto) {
        service.save(dto);
    }

    @Autowired
    private CategoryService categoryService;

    @Override
    public Boolean processDelete(Model model) {
        var dependent = categoryService.dependentProducts(id);
        if(dependent > 0) {
            var viewModel = new CategoryDeleteViewModel()
                .setBreadCrumbs("Category Index / Fail to Delete Category")
                .setDependencies("dependencies", dependent);
            viewModel.build(model);
            return false;
        }
        service.delete(id);
        return true;
    }
}
