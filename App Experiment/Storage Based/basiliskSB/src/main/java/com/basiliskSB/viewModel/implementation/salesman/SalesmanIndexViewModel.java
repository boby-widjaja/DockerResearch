package com.basiliskSB.viewModel.implementation.salesman;

import com.basiliskSB.dto.utility.DropdownDTO;
import com.basiliskSB.viewModel.abstraction.DropdownViewModel;
import com.basiliskSB.viewModel.abstraction.IndexViewModel;
import org.springframework.ui.Model;
import java.util.List;

public class SalesmanIndexViewModel extends IndexViewModel{
    private String employeeNumber;
    private String name;
    private String employeeLevel;
    private String superiorName;
    private List<DropdownDTO> employeeLevelDropdown;

    public SalesmanIndexViewModel setEmployeeLevelDropdown(List<DropdownDTO> employeeLevelDropdown){
        this.employeeLevelDropdown = employeeLevelDropdown;
        return this;
    }

    public SalesmanIndexViewModel(String employeeNumber, String name, String employeeLevel, String superiorName) {
        this.employeeNumber = employeeNumber;
        this.name = name;
        this.employeeLevel = employeeLevel;
        this.superiorName = superiorName;
        setBreadCrumbs("Salesman Index")
            .setTableHeaders(new String[] {"Employee Number", "Full Name", "Level", "Superior"})
            .setActionUrl("/salesman/index");
    }

    @Override
    public void build(Model model) {
        var urlParameters = String.format("&employeeNumber=%s&name=%s&employeeLevel=%s&superiorName=%s", employeeNumber, name, employeeLevel, superiorName);
        model.addAttribute("employeeNumber", employeeNumber);
        model.addAttribute("name", name);
        model.addAttribute("employeeLevel", employeeLevel);
        model.addAttribute("superiorName", superiorName);
        model.addAttribute("employeeLevelDropdown", employeeLevelDropdown);
        super.build(model, urlParameters);
    }
}
