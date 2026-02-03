package com.basiliskSB.validation;

import com.basiliskSB.dto.account.ChangePasswordDTO;
import com.basiliskSB.service.abstraction.AccountService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthenticationValidator implements ConstraintValidator<Authentication, ChangePasswordDTO> {

    @Autowired
    private AccountService accountService;

    @Override
    public boolean isValid(ChangePasswordDTO value, ConstraintValidatorContext context) {
        return accountService.checkPassword(value.getUsername(), value.getOldPassword());
    }
}
