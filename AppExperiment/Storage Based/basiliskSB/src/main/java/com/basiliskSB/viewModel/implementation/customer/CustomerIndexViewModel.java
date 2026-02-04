package com.basiliskSB.viewModel.implementation.customer;

import com.basiliskSB.viewModel.abstraction.IndexViewModel;
import org.springframework.ui.Model;

public class CustomerIndexViewModel extends IndexViewModel {
    private String company;
    private String contact;

    public CustomerIndexViewModel(String company, String contact) {
        this.company = company;
        this.contact = contact;
        setBreadCrumbs("Customer Index")
            .setTableHeaders(new String[] {"Company", "Contact", "Address", "City"})
            .setActionUrl("/customer/index");
    }

    @Override
    public void build(Model model) {
        var urlParameters = String.format("&company=%s&contact=%s", company, contact);
        model.addAttribute("company", company);
        model.addAttribute("contact", contact);
        super.build(model, urlParameters);
    }
}
