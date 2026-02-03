package com.basiliskSB.factory.implementation;

import com.basiliskSB.dto.product.ProductFilterDTO;
import com.basiliskSB.dto.product.ProductGridDTO;
import com.basiliskSB.dto.product.UpsertProductDTO;
import com.basiliskSB.factory.abstraction.DeleteFactory;
import com.basiliskSB.factory.abstraction.IndexFactory;
import com.basiliskSB.factory.abstraction.UpsertFactory;
import com.basiliskSB.service.abstraction.CrudService;
import com.basiliskSB.service.abstraction.ProductService;
import com.basiliskSB.utility.FileHelper;
import com.basiliskSB.utility.MapperHelper;
import com.basiliskSB.utility.ProductImageHandler;
import com.basiliskSB.viewModel.implementation.product.ProductDeleteViewModel;
import com.basiliskSB.viewModel.implementation.product.ProductIndexViewModel;
import com.basiliskSB.viewModel.implementation.product.ProductUpsertViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Scope("singleton")
@Component
public class ProductFactory implements IndexFactory, UpsertFactory, DeleteFactory {

    @Qualifier("productService")
    @Autowired
    private CrudService service;

    @Autowired
    private ProductService productService;

    private Map<String, Object> urlParameters = new HashMap<>();
    private List<Object> categoryDropdown;
    private Integer page;

    @Override
    public Boolean processDelete(Model model) {
        long dependent = productService.dependentOrderDetails((Long)id);
        if(dependent > 0) {
            var viewModel = new ProductDeleteViewModel()
                .setBreadCrumbs("Product Index / Fail to Delete Product")
                .setDependencies("dependencies", dependent);
            viewModel.build(model);
            return false;
        }
        String imagePath = productService.getImagePath(id);
        FileHelper.deleteProductPhoto(imagePath);
        service.delete(id);
        return true;
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

    private String getName(){
        return getUrlParameters().get("name").toString();
    }
    private Long getCategoryId(){
        return (Long)getUrlParameters().get("categoryId");
    }
    private Long getSupplierId(){
        return (Long)getUrlParameters().get("supplierId");
    }

    @Override
    public void processIndex(Model model) {
        var pageCollection = service.getGrid(page, new ProductFilterDTO(getName(), getCategoryId(), getSupplierId()));
        var grid = IndexFactory.getGridDTO(pageCollection, row -> new ProductGridDTO(row));
        var viewModel = new ProductIndexViewModel(getName(), getCategoryId(), getSupplierId())
            .setCategoryDropdown(productService.getCategoryDropdown())
            .setSupplierDropdown(productService.getSupplierDropdown())
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
        var viewModel = new ProductUpsertViewModel()
            .setCategoryDropdown(productService.getCategoryDropdown())
            .setSupplierDropdown(productService.getSupplierDropdown());
        if (id != null) {
            var entity = service.getUpdate((Long)id);
            var dto = new UpsertProductDTO(entity);
            viewModel.setBreadCrumbs("Product Index / Update Product")
                .setType("update")
                .setDto(dto);
        } else {
            UpsertProductDTO dto = new UpsertProductDTO();
            viewModel.setBreadCrumbs("Product Index / Insert Product")
                .setType("insert")
                .setDto(dto);
        }
        viewModel.build(model);
    }

    @Override
    public void processUpsertForm(Model model, Object dto) {
        var id = MapperHelper.getLongField(dto, "id");
        var viewModel = new ProductUpsertViewModel()
            .setCategoryDropdown(productService.getCategoryDropdown())
            .setSupplierDropdown(productService.getSupplierDropdown());
        if (id != null) {
            viewModel.setBreadCrumbs("Product Index / Update Product")
                .setType("update")
                .setDto(dto);
        } else {
            viewModel.setBreadCrumbs("Product Index / Insert Product")
                .setType("insert")
                .setDto(dto);
        }
        viewModel.build(model);
    }

    @Override
    public void save(Object dto) {
        ProductImageHandler.imageFieldHandler((UpsertProductDTO) dto);
        service.save(dto);
    }
}
