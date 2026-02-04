package com.basiliskSB.factory.implementation;
import com.basiliskSB.dto.supplier.SupplierFilterDTO;
import com.basiliskSB.dto.supplier.SupplierGridDTO;
import com.basiliskSB.dto.supplier.UpsertSupplierDTO;
import com.basiliskSB.factory.abstraction.IndexFactory;
import com.basiliskSB.factory.abstraction.DeleteFactory;
import com.basiliskSB.factory.abstraction.UpsertFactory;
import com.basiliskSB.service.abstraction.CrudService;
import com.basiliskSB.viewModel.implementation.supplier.SupplierIndexViewModel;
import com.basiliskSB.viewModel.implementation.supplier.SupplierUpsertViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import java.util.HashMap;
import java.util.Map;

@Scope("singleton")
@Component
public class SupplierFactory implements IndexFactory, UpsertFactory, DeleteFactory {

    @Qualifier("supplierService")
    @Autowired
    private CrudService service;

    private Map<String, Object> urlParameters = new HashMap<>();
    private Integer page;

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

    private String getCompany(){
        return getUrlParameters().get("company").toString();
    }
    private String getContact(){
        return getUrlParameters().get("contact").toString();
    }
    private String getJobTitle(){
        return getUrlParameters().get("jobTitle").toString();
    }

    @Override
    public void processIndex(Model model) {
        var pageCollection = service.getGrid(page, new SupplierFilterDTO(getCompany(), getContact(), getJobTitle()));
        var grid = IndexFactory.getGridDTO(pageCollection, row -> new SupplierGridDTO(row));
        var viewModel = new SupplierIndexViewModel(getCompany(), getContact(), getJobTitle())
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
        var viewModel = new SupplierUpsertViewModel();
        if (id != null) {
            var entity = service.getUpdate(id);
            var dto = new UpsertSupplierDTO(entity);
            viewModel.setBreadCrumbs("Supplier Index / Update Supplier")
                .setType("update")
                .setDto(dto);
        } else {
            var dto = new UpsertSupplierDTO();
            viewModel.setBreadCrumbs("Supplier Index / Insert Supplier")
                .setType("insert")
                .setDto(dto);
        }
        viewModel.build(model);
    }

    @Override
    public void processUpsertForm(Model model, Object dto) {
        var viewModel = new SupplierUpsertViewModel();
        if (id != null) {
            viewModel.setBreadCrumbs("Supplier Index / Update Supplier")
                .setType("update")
                .setDto(dto);
        } else {
            viewModel.setBreadCrumbs("Supplier Index / Insert Supplier")
                .setType("insert")
                .setDto(dto);
        }
        viewModel.build(model);
    }

    @Override
    public void save(Object dto) {
        service.save(dto);
    }

    @Override
    public Boolean processDelete(Model model) {
        service.delete(id);
        return true;
    }
}
