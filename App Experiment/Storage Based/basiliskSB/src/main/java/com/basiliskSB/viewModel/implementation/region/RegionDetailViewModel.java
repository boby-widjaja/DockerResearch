package com.basiliskSB.viewModel.implementation.region;
import com.basiliskSB.dto.utility.DropdownDTO;
import com.basiliskSB.utility.MapperHelper;
import com.basiliskSB.viewModel.abstraction.IndexDetailViewModel;
import org.springframework.ui.Model;
import java.util.List;

public class RegionDetailViewModel extends IndexDetailViewModel {
    private String employeeNumber;
    private String name;
    private String employeeLevel;
    private String superiorName;
    private List<DropdownDTO> employeeLevelDropdown;

    public RegionDetailViewModel setEmployeeLevelDropdown(List<DropdownDTO> employeeLevelDropdown){
        this.employeeLevelDropdown = employeeLevelDropdown;
        return this;
    }

    public RegionDetailViewModel(String employeeNumber, String name, String employeeLevel, String superiorName) {
        this.employeeNumber = employeeNumber;
        this.name = name;
        this.employeeLevel = employeeLevel;
        this.superiorName = superiorName;
        setTableHeaders(new String[] {"Employee Number", "Full Name", "Level", "Superior"})
            .setActionUrl("/region/detail");
    }

    @Override
    public void build(Model model) {
        var urlParameters = String.format("&id=%s&employeeNumber=%s&name=%s&employeeLevel=%s&superiorName=%s", getParentId(), employeeNumber, name, employeeLevel, superiorName);
        model.addAttribute("employeeNumber", employeeNumber);
        model.addAttribute("name", name);
        model.addAttribute("employeeLevel", employeeLevel);
        model.addAttribute("superiorName", superiorName);
        model.addAttribute("employeeLevelDropdown", employeeLevelDropdown);
        var city = MapperHelper.getStringField(getParent(), "city");
        setBreadCrumbs(String.format("Salesman of %s", city));
        super.build(model, urlParameters);
    }
}
