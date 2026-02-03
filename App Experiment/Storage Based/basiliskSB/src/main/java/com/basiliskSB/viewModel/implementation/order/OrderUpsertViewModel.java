package com.basiliskSB.viewModel.implementation.order;
import com.basiliskSB.dto.utility.DropdownDTO;
import com.basiliskSB.viewModel.abstraction.DropdownViewModel;
import com.basiliskSB.viewModel.abstraction.UpsertViewModel;
import org.springframework.ui.Model;

import java.util.List;

public class OrderUpsertViewModel extends UpsertViewModel implements DropdownViewModel {
    private List<DropdownDTO> customerDropdown;
    private List<DropdownDTO> salesmanDropdown;
    private List<DropdownDTO> deliveryDropdown;

    public OrderUpsertViewModel setCustomerDropdown(List<Object> dropdown){
        this.customerDropdown = DropdownViewModel.getDropdownDTO(dropdown);
        return this;
    }

    public OrderUpsertViewModel setSalesmanDropdown(List<Object> dropdown){
        this.salesmanDropdown = DropdownViewModel.getDropdownDTO(dropdown);
        return this;
    }

    public OrderUpsertViewModel setDeliveryDropdown(List<Object> dropdown){
        this.deliveryDropdown = DropdownViewModel.getDropdownDTO(dropdown);
        return this;
    }

    @Override
    public void build(Model model) {
        model.addAttribute("customerDropdown", customerDropdown);
        model.addAttribute("salesmanDropdown", salesmanDropdown);
        model.addAttribute("deliveryDropdown", deliveryDropdown);
        super.build(model);
    }
}
