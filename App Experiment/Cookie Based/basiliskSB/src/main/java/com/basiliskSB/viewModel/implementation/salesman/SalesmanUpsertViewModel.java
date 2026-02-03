package com.basiliskSB.viewModel.implementation.salesman;
import com.basiliskSB.dto.utility.DropdownDTO;
import com.basiliskSB.viewModel.abstraction.DropdownViewModel;
import com.basiliskSB.viewModel.abstraction.UpsertViewModel;
import org.springframework.ui.Model;

import java.util.List;

public class SalesmanUpsertViewModel extends UpsertViewModel implements DropdownViewModel {

    private List<DropdownDTO> employeeLevelDropdown;

    public SalesmanUpsertViewModel setEmployeeLevelDropdown(List<DropdownDTO> employeeLevelDropdown){
        this.employeeLevelDropdown = employeeLevelDropdown;
        return this;
    }

    private List<DropdownDTO> superiorDropdown;

    public SalesmanUpsertViewModel setSuperiorDropdown(List<Object> dropdown){
        this.superiorDropdown = DropdownViewModel.getDropdownDTO(dropdown);
        return this;
    }

    @Override
    public void build(Model model) {
        model.addAttribute("employeeLevelDropdown", employeeLevelDropdown);
        model.addAttribute("superiorDropdown", superiorDropdown);
        super.build(model);
    }
}
