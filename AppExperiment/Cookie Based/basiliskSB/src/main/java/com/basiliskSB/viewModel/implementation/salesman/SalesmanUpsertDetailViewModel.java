package com.basiliskSB.viewModel.implementation.salesman;
import com.basiliskSB.dto.utility.DropdownDTO;
import com.basiliskSB.viewModel.abstraction.DropdownViewModel;
import com.basiliskSB.viewModel.abstraction.UpsertViewModel;
import org.springframework.ui.Model;

import java.util.List;

public class SalesmanUpsertDetailViewModel extends UpsertViewModel implements DropdownViewModel {
    private List<DropdownDTO> regionDropdown;

    public SalesmanUpsertDetailViewModel setRegionDropdown(List<Object> dropdown){
        this.regionDropdown = DropdownViewModel.getDropdownDTO(dropdown);
        return this;
    }

    @Override
    public void build(Model model) {
        model.addAttribute("regionDropdown", regionDropdown);
        super.build(model);
    }
}
