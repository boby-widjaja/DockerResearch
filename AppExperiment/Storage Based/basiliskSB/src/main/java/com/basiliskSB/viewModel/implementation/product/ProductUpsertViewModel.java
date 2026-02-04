package com.basiliskSB.viewModel.implementation.product;

import com.basiliskSB.dto.utility.DropdownDTO;
import com.basiliskSB.viewModel.abstraction.DropdownViewModel;
import com.basiliskSB.viewModel.abstraction.UpsertViewModel;
import org.springframework.ui.Model;

import java.util.List;

public class ProductUpsertViewModel extends UpsertViewModel implements DropdownViewModel {

    private List<DropdownDTO> categoryDropdown;
    private List<DropdownDTO> supplierDropdown;

    public ProductUpsertViewModel setCategoryDropdown(List<Object> dropdown){
        this.categoryDropdown = DropdownViewModel.getDropdownDTO(dropdown);
        return this;
    }
    public ProductUpsertViewModel setSupplierDropdown(List<Object> dropdown){
        this.supplierDropdown = DropdownViewModel.getDropdownDTO(dropdown);
        return this;
    }

    @Override
    public void build(Model model) {
        model.addAttribute("categoryDropdown", categoryDropdown);
        model.addAttribute("supplierDropdown", supplierDropdown);
        super.build(model);
    }
}
