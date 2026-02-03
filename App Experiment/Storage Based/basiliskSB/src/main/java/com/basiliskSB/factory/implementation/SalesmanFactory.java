package com.basiliskSB.factory.implementation;
import com.basiliskSB.dto.salesman.InsertSalesmanDTO;
import com.basiliskSB.dto.salesman.SalesmanFilterDTO;
import com.basiliskSB.dto.salesman.SalesmanGridDTO;
import com.basiliskSB.dto.salesman.UpdateSalesmanDTO;
import com.basiliskSB.dto.utility.Dropdown;
import com.basiliskSB.factory.abstraction.DeleteFactory;
import com.basiliskSB.factory.abstraction.IndexFactory;
import com.basiliskSB.factory.abstraction.UpsertFactory;
import com.basiliskSB.service.abstraction.CrudService;
import com.basiliskSB.service.abstraction.SalesmanService;
import com.basiliskSB.utility.MapperHelper;
import com.basiliskSB.viewModel.implementation.salesman.SalesmanDeleteViewModel;
import com.basiliskSB.viewModel.implementation.salesman.SalesmanIndexViewModel;
import com.basiliskSB.viewModel.implementation.salesman.SalesmanUpsertViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import java.util.HashMap;
import java.util.Map;

@Scope("singleton")
@Component
public class SalesmanFactory implements IndexFactory, UpsertFactory, DeleteFactory {

    @Qualifier("salesmanService")
    @Autowired
    private CrudService service;

    @Autowired
    private SalesmanService salesmanService;

    private Map<String, Object> urlParameters = new HashMap<>();
    private Integer page;

    @Override
    public Boolean processDelete(Model model) {
        var dependentOrders = salesmanService.dependentOrders(employeeNumber);
        long dependentSubordinates = salesmanService.dependentSubordinates(employeeNumber);
        if(dependentOrders > 0 || dependentSubordinates > 0) {
            var viewModel = new SalesmanDeleteViewModel()
                .setBreadCrumbs("Salesman Index / Fail to Delete Salesman")
                .setDependencies("dependentOrders", dependentOrders)
                .setDependencies("dependentSubordinates", dependentSubordinates);
            viewModel.build(model);
            return false;
        }
        service.delete(employeeNumber);
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

    private String getEmployeeNumber(){
        return getUrlParameters().get("employeeNumber").toString();
    }
    private String getName(){
        return getUrlParameters().get("name").toString();
    }
    private String getEmployeeLevel(){
        return getUrlParameters().get("employeeLevel").toString();
    }
    private String getSuperiorName(){
        return getUrlParameters().get("superiorName").toString();
    }

    @Override
    public void processIndex(Model model) {
        var pageCollection = service.getGrid(page, new SalesmanFilterDTO(getEmployeeNumber(), getName(), getEmployeeLevel(), getSuperiorName()));
        var grid = IndexFactory.getGridDTO(pageCollection, row -> new SalesmanGridDTO(row));
        var viewModel = new SalesmanIndexViewModel(getEmployeeNumber(), getName(), getEmployeeLevel(), getSuperiorName())
            .setEmployeeLevelDropdown(Dropdown.getEmployeeLevelDropdown())
            .setGrid(grid)
            .setPage(page)
            .setTotalPages(pageCollection.getTotalPages());
        viewModel.build(model);
    }

    private String employeeNumber;
    @Override
    public void setId(Object id) {
        employeeNumber = (id != null) ? id.toString() : null;
    }
    @Override
    public Object getId() {
        return employeeNumber;
    }

    @Override
    public void processUpsertForm(Model model) {
        var viewModel = new SalesmanUpsertViewModel()
            .setEmployeeLevelDropdown(Dropdown.getEmployeeLevelDropdown())
            .setSuperiorDropdown(salesmanService.getSuperiorDropdown(employeeNumber));
        if (employeeNumber != null) {
            var entity = service.getUpdate(employeeNumber);
            var dto = new UpdateSalesmanDTO(entity);
            viewModel.setBreadCrumbs("Salesman Index / Update Salesman")
                .setType("update")
                .setDto(dto);
        } else {
            var dto = new InsertSalesmanDTO();
            viewModel.setBreadCrumbs("Salesman Index / Insert Salesman")
                .setType("insert")
                .setDto(dto);
        }
        viewModel.build(model);
    }

    @Override
    public void processUpsertForm(Model model, Object dto) {
        var viewModel = new SalesmanUpsertViewModel()
            .setEmployeeLevelDropdown(Dropdown.getEmployeeLevelDropdown())
            .setSuperiorDropdown(salesmanService.getSuperiorDropdown(employeeNumber));
        if (employeeNumber != null) {
            viewModel.setBreadCrumbs("Salesman Index / Update Salesman")
                .setType("update")
                .setDto(dto);
        } else {
            viewModel.setBreadCrumbs("Salesman Index / Insert Salesman")
                .setType("insert")
                .setDto(dto);
        }
        viewModel.build(model);
    }

    @Override
    public void save(Object dto) {
        var superiorEmployeeNumber = MapperHelper.getStringField(dto, "superiorEmployeeNumber");
        if(superiorEmployeeNumber.equals("")){
            MapperHelper.setFieldValue(dto, "superiorEmployeeNumber", null);
        }
        service.save(dto);
    }
}
