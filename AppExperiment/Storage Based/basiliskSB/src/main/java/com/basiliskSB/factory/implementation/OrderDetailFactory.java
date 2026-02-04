package com.basiliskSB.factory.implementation;
import com.basiliskSB.dto.order.OrderDetailGridDTO;
import com.basiliskSB.dto.order.UpsertOrderDetailDTO;
import com.basiliskSB.factory.abstraction.DeleteDetailFactory;
import com.basiliskSB.factory.abstraction.IndexDetailFactory;
import com.basiliskSB.factory.abstraction.IndexFactory;
import com.basiliskSB.factory.abstraction.UpsertDetailFactory;
import com.basiliskSB.service.abstraction.CrudDetailService;
import com.basiliskSB.service.abstraction.OrderService;
import com.basiliskSB.utility.MapperHelper;
import com.basiliskSB.viewModel.implementation.order.OrderDetailViewModel;
import com.basiliskSB.viewModel.implementation.order.OrderUpsertDetailViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import java.util.HashMap;
import java.util.Map;

@Component
public class OrderDetailFactory implements IndexDetailFactory, UpsertDetailFactory, DeleteDetailFactory{

    @Qualifier("orderService")
    @Autowired
    private CrudDetailService detailService;

    @Autowired
    private OrderService orderService;

    private Object parent;

    private String parentId;

    @Override
    public Object getParent() {
        return parent;
    }

    @Override
    public void setParentId(Object parentId) {
        this.parentId = (parentId != null) ? parentId.toString() : null;
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

    @Override
    public void processIndex(Model model) {
        var parent = detailService.getHeader(parentId);
        var pageCollection = detailService.getGridDetail(parentId, page, null);
        var grid = IndexFactory.getGridDTO(pageCollection, (row) -> {
            var unitPrice = MapperHelper.getDoubleField(row, 3);
            var quantity = MapperHelper.getIntegerField(row, 4);
            var discount = MapperHelper.getDoubleField(row, 5);
            var totalPrice = OrderService.calculateTotalPrice(unitPrice, quantity, discount);
            return new OrderDetailGridDTO(row, totalPrice);
        });
        var viewModel = new OrderDetailViewModel()
            .setParent(parent)
            .setParentId(parentId)
            .setGrid(grid)
            .setPage(page)
            .setTotalPages(pageCollection.getTotalPages());
        viewModel.build(model);
    }

    private Object id;
    @Override
    public void setId(Object id) {
        this.id = id;
    }
    @Override
    public Object getId() {
        return id;
    }

    @Override
    public void processUpsertForm(Model model) {
        var viewModel = new OrderUpsertDetailViewModel()
            .setProductDropdown(orderService.getProductDropdown(parentId));
        var productId = MapperHelper.getLongField(id, "productId");
        if(productId != null){
            var entity = detailService.getUpdateDetail(id);
            var dto = new UpsertOrderDetailDTO(entity);
            var productName = orderService.getProductName(MapperHelper.getLongField(entity, "productId"));
            dto.setProductName(productName);
            viewModel.setBreadCrumbs(String.format("Order of %s / Update Detail", parentId))
                .setType("update")
                .setDto(dto);
        } else {
            var dto = new UpsertOrderDetailDTO();
            dto.setInvoiceNumber(parentId);
            viewModel.setBreadCrumbs(String.format("Order of %s / Insert Detail", parentId))
                .setType("insert")
                .setDto(dto);
        }
        viewModel.build(model);
    }

    @Override
    public void processUpsertForm(Model model, Object dto) {
        var viewModel = new OrderUpsertDetailViewModel()
            .setProductDropdown(orderService.getProductDropdown(parentId));
        if(id != null){
            viewModel.setBreadCrumbs(String.format("Order of %s / Update Detail", parentId))
                .setType("update")
                .setDto(dto);
        } else {
            viewModel.setBreadCrumbs(String.format("Order of %s / Insert Detail", parentId))
                .setType("insert")
                .setDto(dto);
        }
        viewModel.build(model);
    }

    @Override
    public void save(Object dto) {
        detailService.saveDetail(dto);
    }

    @Override
    public Boolean processDelete(Model model) {
        var detail = detailService.getUpdateDetail(id);
        String invoiceNumber = MapperHelper.getStringField(detail, "invoiceNumber");
        setParentId(invoiceNumber);
        detailService.deleteDetail(id);
        return null;
    }
}
