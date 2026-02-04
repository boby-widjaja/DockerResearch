package com.basiliskSB.factory.implementation;

import com.basiliskSB.dto.region.RegionFilterDTO;
import com.basiliskSB.dto.region.RegionGridDTO;
import com.basiliskSB.dto.region.UpsertRegionDTO;
import com.basiliskSB.factory.abstraction.IndexFactory;
import com.basiliskSB.factory.abstraction.DeleteFactory;
import com.basiliskSB.factory.abstraction.UpsertFactory;
import com.basiliskSB.service.abstraction.CrudService;
import com.basiliskSB.service.abstraction.RegionService;
import com.basiliskSB.utility.MapperHelper;
import com.basiliskSB.viewModel.implementation.region.RegionIndexViewModel;
import com.basiliskSB.viewModel.implementation.region.RegionUpsertViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import java.util.HashMap;
import java.util.Map;

@Scope("singleton")
@Component
public class RegionFactory implements IndexFactory, UpsertFactory, DeleteFactory {

    @Qualifier("regionService")
    @Autowired
    private CrudService service;

    @Autowired
    private RegionService regionService;

    private Map<String, Object> urlParameters = new HashMap<>();
    private Integer page;

    @Override
    public Boolean processDelete(Model model) {
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

    private String getCity(){
        return getUrlParameters().get("city").toString();
    }

    @Override
    public void processIndex(Model model) {
        var pageCollection = service.getGrid(page, new RegionFilterDTO(getCity()));
        var grid = IndexFactory.getGridDTO(pageCollection, row -> new RegionGridDTO(row));
        var viewModel = new RegionIndexViewModel(getCity())
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
        var viewModel = new RegionUpsertViewModel();
        if (id != null) {
            var entity = service.getUpdate(id);
            var dto = new UpsertRegionDTO(entity);
            viewModel.setBreadCrumbs("Region Index / Update Region")
                .setType("update")
                .setDto(dto);
        } else {
            var dto = new UpsertRegionDTO();
            viewModel.setBreadCrumbs("Region Index / Insert Region")
                .setType("insert")
                .setDto(dto);
        }
        viewModel.build(model);
    }

    @Override
    public void processUpsertForm(Model model, Object dto) {
        var id = MapperHelper.getLongField(dto, "id");
        var viewModel = new RegionUpsertViewModel();
        if (id != null) {
            viewModel.setBreadCrumbs("Region Index / Update Region")
                .setType("update")
                .setDto(dto);
        } else {
            viewModel.setBreadCrumbs("Region Index / Insert Region")
                .setType("insert")
                .setDto(dto);
        }
        viewModel.build(model);
    }

    @Override
    public void save(Object dto) {
        service.save(dto);
    }
}
