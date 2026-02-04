package com.basiliskSB.factory.implementation;
import com.basiliskSB.dto.customer.CustomerFilterDTO;
import com.basiliskSB.dto.customer.CustomerGridDTO;
import com.basiliskSB.dto.customer.UpsertCustomerDTO;
import com.basiliskSB.dto.order.OrderCustomerAddressDTO;
import com.basiliskSB.factory.abstraction.IndexFactory;
import com.basiliskSB.factory.abstraction.GetFactory;
import com.basiliskSB.factory.abstraction.DeleteFactory;
import com.basiliskSB.factory.abstraction.UpsertFactory;
import com.basiliskSB.service.abstraction.CrudService;
import com.basiliskSB.service.abstraction.OrderService;
import com.basiliskSB.utility.MapperHelper;
import com.basiliskSB.viewModel.implementation.customer.CustomerIndexViewModel;
import com.basiliskSB.viewModel.implementation.customer.CustomerUpsertViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import java.util.HashMap;
import java.util.Map;

@Scope("singleton")
@Component
public class CustomerFactory implements IndexFactory, UpsertFactory, DeleteFactory, GetFactory {

    @Qualifier("customerService")
    @Autowired
    private CrudService service;

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

    private String getCompany(){
        return getUrlParameters().get("company").toString();
    }
    private String getContact(){
        return getUrlParameters().get("contact").toString();
    }

    @Override
    public void processIndex(Model model) {
        var pageCollection = service.getGrid(page, new CustomerFilterDTO(getCompany(), getContact()));
        var grid = IndexFactory.getGridDTO(pageCollection, row -> new CustomerGridDTO(row));
        var viewModel = new CustomerIndexViewModel(getCompany(), getContact())
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
        var viewModel = new CustomerUpsertViewModel();
        if (id != null) {
            var entity = service.getUpdate(id);
            var dto = new UpsertCustomerDTO(entity);
            viewModel.setBreadCrumbs("Customer Index / Update Customer")
                .setType("update")
                .setDto(dto);
        } else {
            var dto = new UpsertCustomerDTO();
            viewModel.setBreadCrumbs("Customer Index / Insert Customer")
                .setType("insert")
                .setDto(dto);
        }
        viewModel.build(model);
    }

    @Override
    public void processUpsertForm(Model model, Object dto) {
        var id = MapperHelper.getLongField(dto, "id");
        var viewModel = new CustomerUpsertViewModel();
        if (id != null) {
            viewModel.setBreadCrumbs("Customer Index / Update Customer")
                .setType("update")
                .setDto(dto);
        } else {
            viewModel.setBreadCrumbs("Customer Index / Insert Customer")
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
    private OrderService orderService;

    @Override
    public Object getData() {
        var data = orderService.getCustomer(id);
        var dto = new OrderCustomerAddressDTO(
            MapperHelper.getStringField(data, "address"),
            MapperHelper.getStringField(data, "city")
        );
        return dto;
    }
}
