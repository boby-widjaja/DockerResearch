package com.basiliskSB.validation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import com.basiliskSB.service.abstraction.RegionService;

public class UniqueAssignRegionSalesmanValidator implements ConstraintValidator<UniqueAssignRegionSalesman, Object>{
	@Autowired
	private RegionService regionService;
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
        Long regionIdValue = (Long)(new BeanWrapperImpl(value).getPropertyValue("regionId"));
        String salesmanEmployeeNumberValue = new BeanWrapperImpl(value).getPropertyValue("salesmanEmployeeNumber").toString();
        return !regionService.checkExistingRegionSalesman(regionIdValue, salesmanEmployeeNumberValue);
	}

}
