package com.basiliskSB.service.abstraction;
import java.util.List;

public interface ProductService {
	public Long dependentOrderDetails(Long id);
	public List<Object> getCategoryDropdown();
	public List<Object> getSupplierDropdown();
	public String getImagePath(Long id);
}
