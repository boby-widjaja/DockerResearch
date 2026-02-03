package com.basiliskSB.factory.implementation;
import com.basiliskSB.dto.salesman.AssignRegionDTO;
import com.basiliskSB.dto.salesman.SalesmanDetailGridDTO;
import com.basiliskSB.factory.abstraction.IndexFactory;
import com.basiliskSB.factory.abstraction.DeleteAssociationFactory;
import com.basiliskSB.factory.abstraction.IndexDetailFactory;
import com.basiliskSB.factory.abstraction.UpsertFactory;
import com.basiliskSB.service.abstraction.SalesmanService;
import com.basiliskSB.utility.MapperHelper;
import com.basiliskSB.viewModel.implementation.salesman.SalesmanDetailViewModel;
import com.basiliskSB.viewModel.implementation.salesman.SalesmanUpsertDetailViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import java.util.HashMap;
import java.util.Map;

@Component
public class SalesmanDetailFactory implements IndexDetailFactory, UpsertFactory, DeleteAssociationFactory {

    @Autowired
    private SalesmanService salesmanService;

    private Object parent;

    private String parentId;

    @Override
    public Object getParent() {
        return parent;
    }

    @Override
    public void setParentId(Object parentId) {
        this.parentId = parentId.toString();
    }
    @Override
    public Object getParentId() {
        return parentId;
    }

    private Integer page;

    @Override
    public void setPage(Integer page) {
        this.page = page;
    }
    @Override
    public Integer getPage() {
        return page;
    }

    private Map<String, Object> urlParameters = new HashMap<>();

    @Override
    public void setUrlParameters(String key, Object value) {
        urlParameters.put(key, value);
    }
    @Override
    public Map<String, Object> getUrlParameters() {
        return urlParameters;
    }

    private String getCity(){
        return getUrlParameters().get("city").toString();
    }

    @Override
    public void processIndex(Model model) {
        var parent = salesmanService.getSalesmanHeader(parentId);
        var pageCollection = salesmanService.getRegionGridBySalesman(parentId, page, getCity());
        var grid = IndexFactory.getGridDTO(pageCollection, row -> new SalesmanDetailGridDTO(row));
        var viewModel = new SalesmanDetailViewModel(getCity())
            .setParent(parent)
            .setParentId(parentId)
            .setGrid(grid)
            .setPage(page)
            .setTotalPages(pageCollection.getTotalPages());
        viewModel.build(model);
    }

    private String employeeNumber;

    @Override
    public void setId(Object id) {
        this.employeeNumber = id.toString();
    }

    @Override
    public Object getId() {
        return employeeNumber;
    }

    private String getSalesmanFullName(Object header){
        String firstName = MapperHelper.getStringField(header, "firstName");
        String lastName = MapperHelper.getStringField(header, "lastName");
        String fullName = firstName + ((lastName != null) ? String.format(" %s", lastName) : "");
        return fullName;
    }

    @Override
    public void processUpsertForm(Model model) {
        var parent = salesmanService.getSalesmanHeader(employeeNumber);
        String fullName = getSalesmanFullName(parent);
        var dto = new AssignRegionDTO();
        dto.setSalesmanEmployeeNumber(employeeNumber);
        var viewModel = new SalesmanUpsertDetailViewModel()
            .setRegionDropdown(salesmanService.getRegionDropdown(employeeNumber))
            .setBreadCrumbs(String.format("Salesman Index / Region of %s / Assign Region", fullName))
            .setDto(dto);
        viewModel.build(model);
    }

    @Override
    public void processUpsertForm(Model model, Object dto) {
        var parent = salesmanService.getSalesmanHeader(employeeNumber);
        String fullName = getSalesmanFullName(parent);
        var viewModel = new SalesmanUpsertDetailViewModel()
            .setRegionDropdown(salesmanService.getRegionDropdown(employeeNumber))
            .setBreadCrumbs(String.format("Salesman Index / Region of %s / Assign Region", fullName))
            .setDto(dto);
        viewModel.build(model);
    }

    @Override
    public void save(Object dto) {
        salesmanService.assignRegion(dto);
    }

    private Long regionId;

    @Override
    public void setPairId(Object id) {
        this.regionId = (Long) id;
    }

    @Override
    public Object getPairId() {
        return regionId;
    }

    @Override
    public Boolean processDelete(Model model) {
        salesmanService.detachRegionSalesman(regionId, employeeNumber);
        return true;
    }
}
