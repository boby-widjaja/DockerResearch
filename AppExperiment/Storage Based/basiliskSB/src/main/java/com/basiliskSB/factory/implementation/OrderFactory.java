package com.basiliskSB.factory.implementation;
import com.basiliskSB.dto.order.InvoiceDTO;
import com.basiliskSB.dto.order.OrderFilterDTO;
import com.basiliskSB.dto.order.OrderGridDTO;
import com.basiliskSB.dto.order.UpsertOrderDTO;
import com.basiliskSB.factory.abstraction.IndexFactory;
import com.basiliskSB.factory.abstraction.DeleteFactory;
import com.basiliskSB.factory.abstraction.PDFFactory;
import com.basiliskSB.factory.abstraction.UpsertFactory;
import com.basiliskSB.service.abstraction.CrudDetailService;
import com.basiliskSB.service.abstraction.CrudService;
import com.basiliskSB.service.abstraction.OrderService;
import com.basiliskSB.utility.PDFInvoiceGenerator;
import com.basiliskSB.viewModel.implementation.order.OrderIndexViewModel;
import com.basiliskSB.viewModel.implementation.order.OrderUpsertViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Scope("singleton")
@Component
public class OrderFactory implements IndexFactory, UpsertFactory, DeleteFactory, PDFFactory {

    @Qualifier("orderService")
    @Autowired
    private CrudService service;

    @Qualifier("orderService")
    @Autowired
    private CrudDetailService detailService;

    @Autowired
    private OrderService orderService;

    private Map<String, Object> urlParameters = new HashMap<>();
    private Integer page;

    @Override
    public Boolean processDelete(Model model) {
        service.delete(invoiceNumber);
        return true;
    }

    @Override
    public void setPage(Integer page) {
        this.page = page;
    }
    @Override
    public Integer getPage() {
        return page;
    }
    @Override
    public void setUrlParameters(String key, Object value) {
        urlParameters.put(key, value);
    }
    @Override
    public Map<String, Object> getUrlParameters() {
        return urlParameters;
    }

    private String getInvoiceNumber(){
        var invoiceNumber = getUrlParameters().get("invoiceNumber");
        return (invoiceNumber != null) ? invoiceNumber.toString() : null;
    }
    private Long getCustomerId(){
        var customerId = getUrlParameters().get("customerId");
        return (customerId != null) ? (Long)customerId : null;
    }
    private String getEmployeeNumber(){
        var employeeNumber = getUrlParameters().get("employeeNumber");
        return (employeeNumber != null) ? employeeNumber.toString() : null;
    }
    private Long getDeliveryId(){
        return (Long)getUrlParameters().get("deliveryId");
    }
    private LocalDate getOrderDate(){
        var orderDate = getUrlParameters().get("orderDate");
        LocalDate formattedDate = null;
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if(orderDate != null && !orderDate.equals("")) {
            formattedDate = LocalDate.parse(orderDate.toString(), formatter);
        }
        return formattedDate;
    }
    private String getTextOrderDate(){
        var orderDate = getUrlParameters().get("orderDate");
        if(orderDate != null && !orderDate.equals("")) {
            return orderDate.toString();
        }
        return null;
    }

    @Override
    public void processIndex(Model model) {
        var pageCollection = service.getGrid(page, new OrderFilterDTO(getInvoiceNumber(), getCustomerId(), getEmployeeNumber(), getDeliveryId(), getOrderDate()));
        var grid = IndexFactory.getGridDTO(pageCollection, row -> new OrderGridDTO(row));
        var viewModel = new OrderIndexViewModel(getInvoiceNumber(), getCustomerId(), getEmployeeNumber(), getDeliveryId(), getTextOrderDate())
            .setCustomerDropdown(orderService.getCustomerDropdown())
            .setSalesmanDropdown(orderService.getSalesmanDropdown())
            .setDeliveryDropdown(orderService.getDeliveryDropdown())
            .setGrid(grid)
            .setPage(page)
            .setTotalPages(pageCollection.getTotalPages());
        viewModel.build(model);
    }

    private String invoiceNumber;
    @Override
    public void setId(Object id) {
        this.invoiceNumber = (id != null) ? id.toString() : null;
    }
    @Override
    public Object getId() {
        return invoiceNumber;
    }

    @Override
    public void processUpsertForm(Model model) {
        var viewModel = new OrderUpsertViewModel()
            .setCustomerDropdown(orderService.getCustomerDropdown())
            .setSalesmanDropdown(orderService.getSalesmanDropdown())
            .setDeliveryDropdown(orderService.getDeliveryDropdown());
        if (invoiceNumber != null) {
            var entity = service.getUpdate(invoiceNumber);
            var dto = new UpsertOrderDTO(entity);
            viewModel.setBreadCrumbs("Order Index / Update Order")
                .setType("update")
                .setDto(dto);
        } else {
            var dto = new UpsertOrderDTO();
            viewModel.setBreadCrumbs("Order Index / Insert Order")
                .setType("insert")
                .setDto(dto);
        }
        viewModel.build(model);
    }

    @Override
    public void processUpsertForm(Model model, Object dto) {
        var viewModel = new OrderUpsertViewModel()
            .setCustomerDropdown(orderService.getCustomerDropdown())
            .setSalesmanDropdown(orderService.getSalesmanDropdown())
            .setDeliveryDropdown(orderService.getDeliveryDropdown());
        if (invoiceNumber != null) {
            viewModel.setBreadCrumbs("Order Index / Update Order")
                .setType("update")
                .setDto(dto);
        } else {
            viewModel.setBreadCrumbs("Order Index / Insert Order")
                .setType("insert")
                .setDto(dto);
        }
        viewModel.build(model);
    }

    @Override
    public void save(Object dto) {
        service.save(dto);
    }

    @Override
    public byte[] getPdfByte() throws Exception {
        var invoice = orderService.getInvoice(invoiceNumber);
        var dto = new InvoiceDTO(invoice);
        var pdfBytes = PDFInvoiceGenerator.generateInvoicePdf(dto);
        return pdfBytes;
    }

    private String invoiceFileName;

    @Override
    public void setFileName(String fileName) {
        this.invoiceFileName = fileName;
    }

    @Override
    public String getFileNameDisposition() {
        return String.format("attachment; filename=\"%s.pdf\"", invoiceFileName);
    }
}
