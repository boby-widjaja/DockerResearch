package com.basiliskSB.viewModel.implementation.delivery;

import com.basiliskSB.viewModel.abstraction.DeleteViewModel;
import org.springframework.ui.Model;

public class DeliveryDeleteViewModel extends DeleteViewModel {
    @Override
    public void build(Model model) {
        model.addAttribute("breadCrumbs", breadCrumbs);
        var dependency = dependencies.get("dependencies");
        model.addAttribute("dependencies", dependency);
    }
}
