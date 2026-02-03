package com.basiliskSB.validation;
import com.basiliskSB.dto.delivery.UpsertDeliveryDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.basiliskSB.service.abstraction.DeliveryService;

public class UniqueDeliveryCompanyValidator implements ConstraintValidator<UniqueDeliveryCompany, UpsertDeliveryDTO> {
	@Autowired
	private DeliveryService deliveryService;
    
	@Override
	public boolean isValid(UpsertDeliveryDTO value, ConstraintValidatorContext context) {
		return !deliveryService.checkExistingDeliveryName(value.getId(), value.getCompanyName());
	}

}
