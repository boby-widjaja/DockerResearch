package com.basiliskSB.viewModel.implementation.salesman;
import com.basiliskSB.utility.MapperHelper;
import com.basiliskSB.viewModel.abstraction.IndexDetailViewModel;
import org.springframework.ui.Model;

public class SalesmanDetailViewModel extends IndexDetailViewModel {
    private String city;

    public SalesmanDetailViewModel(String city) {
        this.city = city;
        setTableHeaders(new String[] {"City", "Remark"})
            .setActionUrl("/salesman/detail");
    }

    private String getSalesmanFullName(Object parent){
        String firstName = MapperHelper.getStringField(parent, "firstName");
        String lastName = MapperHelper.getStringField(parent, "lastName");
        String fullName = firstName + ((lastName != null) ? String.format(" %s", lastName) : "");
        return fullName;
    }

    @Override
    public void build(Model model) {
        var urlParameters = String.format("&employeeNumber=%s&city=%s", getParentId(), city);
        model.addAttribute("city", city);
        var fullName = getSalesmanFullName(getParent());
        setBreadCrumbs(String.format("Salesman Index / Region of %s", fullName));
        model.addAttribute("headerFullName", fullName);
        super.build(model, urlParameters);
    }
}
