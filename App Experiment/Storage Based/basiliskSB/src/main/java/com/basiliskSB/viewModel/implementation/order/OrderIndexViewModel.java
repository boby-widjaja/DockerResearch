package com.basiliskSB.viewModel.implementation.order;

import com.basiliskSB.dto.utility.DropdownDTO;
import com.basiliskSB.viewModel.abstraction.DropdownViewModel;
import com.basiliskSB.viewModel.abstraction.IndexViewModel;
import com.basiliskSB.viewModel.implementation.product.ProductIndexViewModel;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrderIndexViewModel extends IndexViewModel implements DropdownViewModel {
    private String invoiceNumber;
    private Long customerId;
    private String employeeNumber;
    private Long deliveryId;
    private String orderDate;
    private List<DropdownDTO> customerDropdown;
    private List<DropdownDTO> salesmanDropdown;
    private List<DropdownDTO> deliveryDropdown;

    public OrderIndexViewModel setCustomerDropdown(List<Object> dropdown){
        this.customerDropdown = DropdownViewModel.getDropdownDTO(dropdown);
        return this;
    }

    public OrderIndexViewModel setSalesmanDropdown(List<Object> dropdown){
        this.salesmanDropdown = DropdownViewModel.getDropdownDTO(dropdown);
        return this;
    }

    public OrderIndexViewModel setDeliveryDropdown(List<Object> dropdown){
        this.deliveryDropdown = DropdownViewModel.getDropdownDTO(dropdown);
        return this;
    }

    public OrderIndexViewModel(String invoiceNumber, Long customerId, String employeeNumber, Long deliveryId, String orderDate) {
        this.invoiceNumber = invoiceNumber;
        this.customerId = customerId;
        this.employeeNumber = employeeNumber;
        this.deliveryId = deliveryId;
        this.orderDate = orderDate;
        setBreadCrumbs("Order Index")
            .setTableHeaders(new String[] {"Invoice Number", "Customer", "Salesman", "Order Date", "Delivery"})
            .setActionUrl("/order/index");
    }

    @Override
    public void build(Model model) {
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if(orderDate == null || orderDate.equals("")){
            orderDate = formatter.format(LocalDate.now());
        }
        var urlParameters = String.format("&invoiceNumber=%s&customerId=%s&employeeNumber=%s&deliveryId=%s&orderDate=%s", invoiceNumber,
            (customerId != null) ? customerId : "" ,
            (employeeNumber != null) ? employeeNumber : "",
            (deliveryId != null) ? deliveryId : "",
            orderDate);
        model.addAttribute("invoiceNumber", invoiceNumber);
        model.addAttribute("customerId", customerId);
        model.addAttribute("employeeNumber", employeeNumber);
        model.addAttribute("deliveryId", deliveryId);
        model.addAttribute("orderDate", orderDate);
        model.addAttribute("customerDropdown", customerDropdown);
        model.addAttribute("salesmanDropdown", salesmanDropdown);
        model.addAttribute("deliveryDropdown", deliveryDropdown);
        super.build(model, urlParameters);
    }
}
