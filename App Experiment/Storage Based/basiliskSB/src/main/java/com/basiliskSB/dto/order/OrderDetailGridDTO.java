package com.basiliskSB.dto.order;
import com.basiliskSB.utility.MapperHelper;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

@Schema(description = "Ditunjukan di order detail grid di halaman detail.")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDetailGridDTO {

	@Schema(description = "Order Detail PK Invoice Number")
	private String invoiceNumber;

	@Schema(description = "Order Detail PK Product Id")
	private Long productId;

	@Schema(description = "Nama product yang dibeli di invoice.")
	private String product;

	@Schema(description = "Harga product saat transaksi.")
	private Double price;

	@Schema(description = "Kuantitas product yang dibeli.")
	private Integer quantity;

	@Schema(description = "Persentase diskon untuk product ini.")
	private Double discount;

	@Schema(description = "Harga total dari hasil perhitungan kuantitas, harga dan diskon.")
	private Double totalPrice;

	public OrderDetailGridDTO(String invoiceNumber, Long productId, String product, Double price, Integer quantity, Double discount) {
		this.invoiceNumber = invoiceNumber;
		this.productId = productId;
		this.product = product;
		this.price = price;
		this.quantity = quantity;
		this.discount = discount;
	}

	public OrderDetailGridDTO(Object row, Double totalPrice) {
		invoiceNumber = MapperHelper.getStringField(row, 0);
		productId = MapperHelper.getLongField(row, 1);
		product = MapperHelper.getStringField(row, 2);
		price = MapperHelper.getDoubleField(row, 3);
		quantity = MapperHelper.getIntegerField(row, 4);
		discount = MapperHelper.getDoubleField(row, 5);
		this.totalPrice = totalPrice;
	}
}
