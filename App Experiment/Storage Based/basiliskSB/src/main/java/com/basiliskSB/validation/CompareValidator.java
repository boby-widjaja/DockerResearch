package com.basiliskSB.validation;
import com.basiliskSB.utility.MapperHelper;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

public class CompareValidator implements ConstraintValidator<Compare, Object> {

    private String firstField;
    private String secondField;
	
	@Override
	public void initialize(Compare constraintAnnotation) {
        this.firstField = constraintAnnotation.firstField();
        this.secondField = constraintAnnotation.secondField();
	}
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
        var firstValue = MapperHelper.getStringField(value, firstField);
        var secondValue = MapperHelper.getStringField(value, secondField);
        return (firstValue.equals(secondValue));
	}

}
