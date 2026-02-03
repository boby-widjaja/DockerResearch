package com.basiliskSB.viewModel.implementation.delivery;

import com.basiliskSB.viewModel.abstraction.IndexViewModel;
import org.springframework.ui.Model;

public class DeliveryIndexViewModel extends IndexViewModel {
    private String company;

    public DeliveryIndexViewModel(String company) {
        this.company = company;
        setBreadCrumbs("Delivery Index")
            .setTableHeaders(new String[] {"Company", "Phone", "Cost"})
            .setActionUrl("/delivery/index");
    }

    @Override
    public void build(Model model) {
        var urlParameters = String.format("&company=%s", company);
        model.addAttribute("company", company);
        super.build(model, urlParameters);
    }
}
