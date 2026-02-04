package com.basiliskSB.viewModel.implementation.order;
import com.basiliskSB.utility.MapperHelper;
import com.basiliskSB.viewModel.abstraction.IndexDetailViewModel;
import org.springframework.ui.Model;

public class OrderDetailViewModel extends IndexDetailViewModel {

    public OrderDetailViewModel() {
        setTableHeaders(new String[] {"Product", "Price Per Unit", "Quantity", "Discount", "Total"})
            .setActionUrl("/order/detail");
    }

    @Override
    public void build(Model model) {
        var urlParameters = String.format("&invoiceNumber=%s", getParentId());
        var invoiceNumber = MapperHelper.getStringField(getParent(), "invoiceNumber");
        setBreadCrumbs(String.format("Order Index / Order of %s", invoiceNumber));
        super.build(model, urlParameters);
    }
}
