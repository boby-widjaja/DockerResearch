package com.basiliskSB.dto.order;

import com.basiliskSB.utility.FormatHelper;
import com.basiliskSB.utility.MapperHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InvoiceDetailDTO {
    private String productName;
    private String quantity;
    private String unitPrice;
    private String discount;
    private String subTotal;

    public InvoiceDetailDTO(Object object) {
        productName = MapperHelper.getStringField(object, "productName");
        quantity = MapperHelper.getIntegerField(object, "quantity").toString();
        unitPrice = FormatHelper.formatMoney(MapperHelper.getDoubleField(object, "unitPrice"));
        var discountPercentage = FormatHelper.formatPercentage(MapperHelper.getDoubleField(object, "discount") / 100);
        var totalDiscount = FormatHelper.formatMoney(MapperHelper.getDoubleField(object, "totalDiscount"));
        discount = String.format("%s  (%s)", discountPercentage, totalDiscount);
        subTotal = FormatHelper.formatMoney(MapperHelper.getDoubleField(object, "subTotal"));
    }
}
