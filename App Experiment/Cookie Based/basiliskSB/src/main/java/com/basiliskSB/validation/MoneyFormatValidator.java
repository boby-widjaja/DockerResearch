package com.basiliskSB.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MoneyFormatValidator implements ConstraintValidator<MoneyFormat, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try{
            Double.parseDouble(value);
            return true;
        }catch (Exception exception){
            return false;
        }
    }
}
