package com.basiliskSB.service.abstraction;

public interface DeliveryService {
	public Long dependentOrders(long id);
	public Boolean checkExistingDeliveryName(Long id, String company);
}
