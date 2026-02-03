package com.basiliskSB.viewModel.implementation.order;
import com.basiliskSB.dto.utility.DropdownDTO;
import com.basiliskSB.viewModel.abstraction.DropdownViewModel;
import com.basiliskSB.viewModel.abstraction.UpsertViewModel;
import org.springframework.ui.Model;

import java.util.List;

public class OrderUpsertDetailViewModel extends UpsertViewModel implements DropdownViewModel {
    private List<DropdownDTO> productDropdown;


    public OrderUpsertDetailViewModel setProductDropdown(List<Object> dropdown){
        this.productDropdown = DropdownViewModel.getDropdownDTO(dropdown);
        return this;
    }

    @Override
    public void build(Model model) {
        model.addAttribute("productDropdown", productDropdown);
        super.build(model);
    }
}
