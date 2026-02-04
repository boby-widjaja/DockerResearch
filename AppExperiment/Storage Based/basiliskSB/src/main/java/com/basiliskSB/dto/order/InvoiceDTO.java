package com.basiliskSB.dto.order;
import com.basiliskSB.utility.FormatHelper;
import com.basiliskSB.utility.MapperHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InvoiceDTO {
    private String invoiceNumber;
    private String orderDate;
    private String customerCompany;
    private String deliveryCompany;
    private String deliveryAddress;
    private String deliveryCityAndPostalCode;
    private String receivedBy;
    private String deliveryCost;
    private String totalCost;
    private List<InvoiceDetailDTO> details = new ArrayList<>();

    public InvoiceDTO(Object object) {
        invoiceNumber = MapperHelper.getStringField(object, "invoiceNumber");
        orderDate = FormatHelper.formatLongDate(MapperHelper.getLocalDateField(object, "orderDate"));
        customerCompany = MapperHelper.getStringField(object, "customerCompany");
        deliveryCompany = MapperHelper.getStringField(object, "deliveryCompany");
        deliveryAddress = MapperHelper.getStringField(object, "deliveryAddress");
        var deliveryCity = MapperHelper.getStringField(object, "deliveryCity");
        var deliveryPostalCode = MapperHelper.getStringField(object, "deliveryPostalCode");
        deliveryCityAndPostalCode = String.format("%s (%s)", deliveryCity, deliveryPostalCode);
        receivedBy = MapperHelper.getStringField(object, "receivedBy");
        deliveryCost = FormatHelper.formatMoney(MapperHelper.getDoubleField(object, "deliveryCost"));
        totalCost = FormatHelper.formatMoney(MapperHelper.getDoubleField(object, "totalCost"));
        var invoiceDetails = (List<Object>) MapperHelper.getFieldValue(object, "details");
        for(var invoiceDetail : invoiceDetails){
            details.add(new InvoiceDetailDTO(invoiceDetail));
        }
    }
}
