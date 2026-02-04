package com.basiliskSB.entity;
import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@IdClass(OrderDetailId.class)
@Entity
@Table(name="OrderDetails")
public class OrderDetail {
	@Id
	@Column(name="InvoiceNumber")
	private String invoiceNumber;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="InvoiceNumber", insertable=false, updatable=false)
	private Order order;

	@Id
	@Column(name="ProductId")
	private Long productId;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="ProductId", insertable=false, updatable=false)
	private Product product;
	
	@Column(name="UnitPrice")
	private Double unitPrice;
	
	@Column(name="Quantity")
	private Integer quantity;
	
	@Column(name="Discount")
	private Double discount;

	public OrderDetail(String invoiceNumber, Long productId, Double unitPrice, Integer quantity, Double discount) {
		this.invoiceNumber = invoiceNumber;
		this.productId = productId;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		this.discount = discount;
	}
}
