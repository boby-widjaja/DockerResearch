package com.basiliskSB.validation;
import java.lang.annotation.*;
import jakarta.validation.*;

@Constraint(validatedBy = FutureParadoxValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FutureParadox {
	
	public Class<?>[] groups() default {};
	public Class<? extends Payload>[] payload() default {};
	public String message();

}
