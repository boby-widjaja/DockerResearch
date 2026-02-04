package com.basiliskSB.validation;
import java.lang.annotation.*;
import jakarta.validation.*;

@Constraint(validatedBy = UniqueDeliveryCompanyValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueDeliveryCompany {

	public Class<?>[] groups() default {};
	public Class<? extends Payload>[] payload() default {};
	public String message();
}
