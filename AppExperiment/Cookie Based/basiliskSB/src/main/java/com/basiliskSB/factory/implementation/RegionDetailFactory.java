package com.basiliskSB.factory.implementation;
import com.basiliskSB.dto.region.AssignSalesmanDTO;
import com.basiliskSB.dto.region.RegionDetailGridDTO;
import com.basiliskSB.dto.utility.Dropdown;
import com.basiliskSB.factory.abstraction.DeleteAssociationFactory;
import com.basiliskSB.factory.abstraction.IndexDetailFactory;
import com.basiliskSB.factory.abstraction.IndexFactory;
import com.basiliskSB.factory.abstraction.UpsertFactory;
import com.basiliskSB.service.abstraction.RegionService;
import com.basiliskSB.utility.MapperHelper;
import com.basiliskSB.viewModel.implementation.region.RegionDetailViewModel;
import com.basiliskSB.viewModel.implementation.region.RegionUpsertDetailViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import java.util.HashMap;
import java.util.Map;

@Component
public class RegionDetailFactory implements IndexDetailFactory, UpsertFactory, DeleteAssociationFactory {

    @Autowired
    private RegionService regionService;

    private Object parent;

    private Long parentId;

    @Override
    public Object getParent() {
        return parent;
    }
    @Override
    public void setParentId(Object parentId) {
        this.parentId = (Long)parentId;
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
        var parent = regionService.getRegionHeader(parentId);
        var pageCollection = regionService.getSalesmanGridByRegion(parentId, page, getEmployeeNumber(), getName(), getEmployeeLevel(), getSuperiorName());
        var grid = IndexFactory.getGridDTO(pageCollection, row -> new RegionDetailGridDTO(row));
        var viewModel = new RegionDetailViewModel(getEmployeeNumber(), getName(), getEmployeeLevel(), getSuperiorName())
            .setEmployeeLevelDropdown(Dropdown.getEmployeeLevelDropdown())
            .setParent(parent)
            .setParentId(parentId)
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
        var parent = regionService.getRegionHeader(id);
        var city = MapperHelper.getStringField(parent, "city");
        var dto  = new AssignSalesmanDTO();
        dto.setRegionId(id);
        var viewModel = new RegionUpsertDetailViewModel()
            .setSalesmanDropdown(regionService.getSalesmanDropdown(id))
            .setBreadCrumbs(String.format("Region Index / Salesman of %s / Assign Salesman", city))
            .setDto(dto);
        viewModel.build(model);
    }

    @Override
    public void processUpsertForm(Model model, Object dto) {
        var parent = regionService.getRegionHeader(id);
        var city = MapperHelper.getStringField(parent, "city");
        var viewModel = new RegionUpsertDetailViewModel()
            .setSalesmanDropdown(regionService.getSalesmanDropdown(id))
            .setBreadCrumbs(String.format("Region Index / Salesman of %s / Assign Salesman", city))
            .setDto(dto);
        viewModel.build(model);
    }

    @Override
    public void save(Object dto) {
        regionService.assignSalesman(dto);
    }

    private String employeeNumber;

    @Override
    public void setPairId(Object id) {
        this.employeeNumber = id.toString();
    }
    @Override
    public Object getPairId() {
        return employeeNumber;
    }

    @Override
    public Boolean processDelete(Model model) {
        regionService.detachRegionSalesman(id, employeeNumber);
        return true;
    }
}
