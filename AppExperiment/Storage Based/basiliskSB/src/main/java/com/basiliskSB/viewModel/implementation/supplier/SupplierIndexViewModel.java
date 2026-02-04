package com.basiliskSB.viewModel.implementation.supplier;

import com.basiliskSB.viewModel.abstraction.IndexViewModel;
import org.springframework.ui.Model;

public class SupplierIndexViewModel extends IndexViewModel {

    private String company;
    private String contact;
    private String jobTitle;

    public SupplierIndexViewModel(String company, String contact, String jobTitle) {
        this.company = company;
        this.contact = contact;
        this.jobTitle = jobTitle;
        setBreadCrumbs("SupplierIndex")
            .setTableHeaders(new String[]{"Company", "Contact", "Job Title"})
            .setActionUrl("/supplier/index");
    }

    @Override
    public void build(Model model) {
        var urlParameters = String.format("&company=%s&contact=%s&jobTitle=%s", company, contact, jobTitle);
        model.addAttribute("company", company);
        model.addAttribute("contact", contact);
        model.addAttribute("jobTitle", jobTitle);
        super.build(model, urlParameters);
    }
}
