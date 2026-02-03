package com.basiliskSB.viewModel.implementation.category;

import com.basiliskSB.viewModel.abstraction.IndexViewModel;
import org.springframework.ui.Model;

public class CategoryIndexViewModel extends IndexViewModel {

    private String name;

    public CategoryIndexViewModel(String name) {
        super();
        this.name = name;
        setBreadCrumbs("Category Index")
            .setTableHeaders(new String[] {"Name", "Description"})
            .setActionUrl("/category/index");
    }

    @Override
    public void build(Model model) {
        var urlParameters = String.format("&name=%s", name);
        model.addAttribute("name", name);
        super.build(model, urlParameters);
    }
}
