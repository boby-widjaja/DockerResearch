package com.basiliskSB.validation;
import com.basiliskSB.dto.category.UpsertCategoryDTO;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.basiliskSB.service.abstraction.CategoryService;

public class UniqueCategoryNameValidator implements ConstraintValidator<UniqueCategoryName, UpsertCategoryDTO> {

	@Autowired
	private CategoryService categoryService;	
	
	@Override
	public boolean isValid(UpsertCategoryDTO value, ConstraintValidatorContext context) {
		return !categoryService.checkExistingCategoryName(value.getId(), value.getName());
	}

}
