package com.basiliskSB.service.abstraction;

import java.util.*;

public interface OrderService {
	public Boolean checkExistingOrder(String invoiceNumber);
	public List<Object> getCustomerDropdown();
	public List<Object> getSalesmanDropdown();
	public List<Object> getDeliveryDropdown();
	public List<Object> getProductDropdown(String invoiceNumber);
	public Object getCustomer(Long customerId);
	public Object getInvoice(String invoiceNumber);
	public String getProductName(Long productId);

	public static Double calculateTotalPrice(Double price, Integer quantity, Double discount) {
		Double totalPrice = ((100 - discount) / 100) * (quantity * price);
		return totalPrice;
	}
}
