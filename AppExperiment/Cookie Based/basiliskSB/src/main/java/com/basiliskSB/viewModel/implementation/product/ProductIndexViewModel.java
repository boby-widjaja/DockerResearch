package com.basiliskSB.viewModel.implementation.product;

import com.basiliskSB.dto.utility.DropdownDTO;
import com.basiliskSB.viewModel.abstraction.DropdownViewModel;
import com.basiliskSB.viewModel.abstraction.IndexViewModel;
import org.springframework.ui.Model;
import java.util.List;

public class ProductIndexViewModel extends IndexViewModel implements DropdownViewModel {
    private String name;
    private Long categoryId;
    private Long supplierId;

    private List<DropdownDTO> categoryDropdown;
    private List<DropdownDTO> supplierDropdown;

    public ProductIndexViewModel setCategoryDropdown(List<Object> dropdown){
        this.categoryDropdown = DropdownViewModel.getDropdownDTO(dropdown);
        return this;
    }
    public ProductIndexViewModel setSupplierDropdown(List<Object> dropdown){
        this.supplierDropdown = DropdownViewModel.getDropdownDTO(dropdown);
        return this;
    }

    public ProductIndexViewModel(String name, Long categoryId, Long supplierId) {
        this.name = name;
        this.categoryId = categoryId;
        this.supplierId = supplierId;
        setBreadCrumbs("Product Index")
            .setTableHeaders(new String[] {})
            .setActionUrl("/product/index");
    }

    @Override
    public void build(Model model) {
        var urlParameters = String.format("&name=%s&categoryId=%s&supplierId=%s", name,
            (categoryId != null) ? categoryId : "",
            (supplierId != null) ? supplierId : ""
        );
        model.addAttribute("name", name);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("supplierId", supplierId);
        model.addAttribute("categoryDropdown", categoryDropdown);
        model.addAttribute("supplierDropdown", supplierDropdown);
        super.build(model, urlParameters);
    }
}
