package com.basiliskSB.service.abstraction;

public interface CategoryService {
	public Long dependentProducts(Long id);
	public Boolean checkExistingCategoryName(Long id, String name);
}
