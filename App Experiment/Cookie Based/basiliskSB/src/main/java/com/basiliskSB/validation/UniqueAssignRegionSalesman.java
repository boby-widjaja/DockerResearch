package com.basiliskSB.validation;
import java.lang.annotation.*;
import jakarta.validation.*;

@Constraint(validatedBy = UniqueAssignRegionSalesmanValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueAssignRegionSalesman {

	public Class<?>[] groups() default {};
	public Class<? extends Payload>[] payload() default {};
	public String message();
}
