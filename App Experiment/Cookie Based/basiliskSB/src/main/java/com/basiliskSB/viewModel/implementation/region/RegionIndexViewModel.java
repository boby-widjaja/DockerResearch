package com.basiliskSB.viewModel.implementation.region;

import com.basiliskSB.viewModel.abstraction.IndexViewModel;
import org.springframework.ui.Model;

public class RegionIndexViewModel extends IndexViewModel {
    private String city;

    public RegionIndexViewModel(String city) {
        this.city = city;
        setBreadCrumbs("Region Index")
            .setTableHeaders(new String[] {"City", "Remark"})
            .setActionUrl("/region/index");
    }

    @Override
    public void build(Model model) {
        var urlParameters = String.format("&city=%s", city);
        model.addAttribute("city", city);
        super.build(model, urlParameters);
    }
}
