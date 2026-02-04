package com.basiliskSB.dto.order;

import com.basiliskSB.utility.MapperHelper;
import com.basiliskSB.validation.After;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Schema(description = "Data order dalam form yang akan digunakan untuk insert atau update.")
@NoArgsConstructor
@AllArgsConstructor
@After(message="You can't possibly ship the order before ordering.",
    previousDateField="orderDate", subsequentDateField="shippedDate")
@After(message="You due date estimation is impossible, it happened before ship date.",
    previousDateField="shippedDate", subsequentDateField="dueDate")
@Getter
@Setter
public class UpsertOrderDTO {
    @Schema(description = "Nomor invoice PK.")
    private String invoiceNumber;

    @Schema(description = "Customer ID FK.")
    @NotNull(message="Customer is required.")
    private Long customerId;

    @Schema(description = "Salesman Employee Number FK.")
    @NotBlank(message="Sales is required.")
    private String salesEmployeeNumber;

    @Schema(description = "Tanggal pemesanan dalam format yyyy-MM-dd.")
    @NotNull(message="Order date is required.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate orderDate;

    @Schema(description = "Tanggal pengiriman dalam format yyyy-MM-dd.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate shippedDate;

    @Schema(description = "Tanggal sampai pengiriman dalam format yyyy-MM-dd.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    @Schema(description = "Delivery ID FK.")
    @NotNull(message="Delivery company is required.")
    private Long deliveryId;

    @Schema(description = "Alamat tujuan pengiriman, maximum 200 characters.")
    @NotBlank(message="Destination address is required.")
    @Size(max=200, message="Destination address can't be more than 200 characters.")
    private String destinationAddress;

    @Schema(description = "Kota tujuan pengiriman, maximum 20 characters.")
    @NotBlank(message="Destination city is required.")
    @Size(max=20, message="Destination city can't be more than 20 characters.")
    private String destinationCity;

    @Schema(description = "Kode pos alamat tujuan, maximum 5 characters.")
    @NotBlank(message="Destination postal code is required.")
    @Size(max=5, message="Destination postal code can't be more than 5 characters.")
    private String destinationPostalCode;

    public UpsertOrderDTO(Object entity) {
        invoiceNumber = MapperHelper.getStringField(entity, "invoiceNumber");
        customerId = MapperHelper.getLongField(entity, "customerId");
        salesEmployeeNumber = MapperHelper.getStringField(entity, "salesEmployeeNumber");
        orderDate = MapperHelper.getLocalDateField(entity, "orderDate");
        shippedDate = MapperHelper.getLocalDateField(entity, "shippedDate");
        dueDate = MapperHelper.getLocalDateField(entity, "dueDate");
        deliveryId = MapperHelper.getLongField(entity, "deliveryId");
        destinationAddress = MapperHelper.getStringField(entity, "destinationAddress");
        destinationCity = MapperHelper.getStringField(entity, "destinationCity");
        destinationPostalCode = MapperHelper.getStringField(entity, "destinationPostalCode");
    }
}
