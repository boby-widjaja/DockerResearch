package com.basiliskSB.viewModel.implementation.region;
import com.basiliskSB.dto.utility.DropdownDTO;
import com.basiliskSB.viewModel.abstraction.DropdownViewModel;
import com.basiliskSB.viewModel.abstraction.UpsertViewModel;
import org.springframework.ui.Model;
import java.util.List;

public class RegionUpsertDetailViewModel extends UpsertViewModel implements DropdownViewModel {

    private List<DropdownDTO> salesmanDropdown;

    public RegionUpsertDetailViewModel setSalesmanDropdown(List<Object> dropdown){
        this.salesmanDropdown = DropdownViewModel.getDropdownDTO(dropdown);
        return this;
    }

    @Override
    public void build(Model model) {
        model.addAttribute("salesmanDropdown", salesmanDropdown);
        super.build(model);
    }
}
