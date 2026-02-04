package com.basiliskSB.factory.implementation;
import com.basiliskSB.dto.delivery.DeliveryFilterDTO;
import com.basiliskSB.dto.delivery.DeliveryGridDTO;
import com.basiliskSB.dto.delivery.UpsertDeliveryDTO;
import com.basiliskSB.factory.abstraction.DeleteFactory;
import com.basiliskSB.factory.abstraction.IndexFactory;
import com.basiliskSB.factory.abstraction.UpsertFactory;
import com.basiliskSB.service.abstraction.CrudService;
import com.basiliskSB.service.abstraction.DeliveryService;
import com.basiliskSB.utility.MapperHelper;
import com.basiliskSB.viewModel.implementation.delivery.DeliveryDeleteViewModel;
import com.basiliskSB.viewModel.implementation.delivery.DeliveryIndexViewModel;
import com.basiliskSB.viewModel.implementation.delivery.DeliveryUpsertViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import java.util.HashMap;
import java.util.Map;

@Scope("singleton")
@Component
public class DeliveryFactory implements IndexFactory, UpsertFactory, DeleteFactory {

    @Qualifier("deliveryService")
    @Autowired
    private CrudService service;

    @Autowired
    private DeliveryService deliveryService;

    private Map<String, Object> urlParameters = new HashMap<>();
    private Integer page;

    @Override
    public Boolean processDelete(Model model) {
        var dependent = deliveryService.dependentOrders(id);
        if(dependent > 0) {
            var viewModel = new DeliveryDeleteViewModel()
                .setBreadCrumbs("Delivery Index / Fail to Delete Delivery")
                .setDependencies("dependencies", dependent);
            viewModel.build(model);
            return false;
        }
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

    private String getCompany(){
        return getUrlParameters().get("company").toString();
    }

    @Override
    public void processIndex(Model model) {
		var pageCollection = service.getGrid(page, new DeliveryFilterDTO(getCompany()));
		var grid = IndexFactory.getGridDTO(pageCollection, row -> new DeliveryGridDTO(row));
        var viewModel = new DeliveryIndexViewModel(getCompany())
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
        var viewModel = new DeliveryUpsertViewModel();
        if (id != null) {
            var entity = service.getUpdate(id);
            var dto = new UpsertDeliveryDTO(entity);
            viewModel.setBreadCrumbs("Delivery Index / Update Delivery")
                .setType("update")
                .setDto(dto);
        } else {
            var dto = new UpsertDeliveryDTO();
            viewModel.setBreadCrumbs("Delivery Index / Insert Delivery")
                .setType("insert")
                .setDto(dto);
        }
        viewModel.build(model);
    }

    @Override
    public void processUpsertForm(Model model, Object dto) {
        var id = MapperHelper.getLongField(dto, "id");
        var viewModel = new DeliveryUpsertViewModel();
        if (id != null) {
            viewModel.setBreadCrumbs("Delivery Index / Update Delivery")
                .setType("update")
                .setDto(dto);
        } else {
            viewModel.setBreadCrumbs("Delivery Index / Insert Delivery")
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
