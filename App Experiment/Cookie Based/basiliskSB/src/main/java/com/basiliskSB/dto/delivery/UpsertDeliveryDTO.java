package com.basiliskSB.dto.delivery;
import com.basiliskSB.validation.MoneyFormat;
import com.basiliskSB.validation.UniqueDeliveryCompany;
import jakarta.validation.constraints.*;

import com.basiliskSB.utility.MapperHelper;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data dari form delivery yang digunakan untuk insert dan update.")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@UniqueDeliveryCompany(message = "Delivery company name is already exist.")
public class UpsertDeliveryDTO {

	@Schema(description = "PK Delivery")
	private Long id;

	@Schema(description = "Nama perusahaan delivery, maximum 50 characters.")
	@NotBlank(message="Company name is required.")
	@Size(max=50, message="Company name can't be no more than 50 characters.")
	private String companyName;

	@Schema(description = "Nomor telphone dari perusahaan delivery, maximum 20 characters.")
	@Size(max=20, message="Phone can't be no more than 20 characters.")
	private String phone;

	@Schema(description = "Ongkos dalam bilangan decimal dengan 2 angka dibelakang koma.")
	@NotNull(message="Cost is required.")
	@Digits(integer=12, fraction=2, message="Must be a decimal value with 2 decimal points.")
	private Double cost;

	public UpsertDeliveryDTO(Object entity) {
		id = MapperHelper.getLongField(entity, "id");
		companyName = MapperHelper.getStringField(entity, "companyName");
		phone = MapperHelper.getStringField(entity, "phone");
		cost = MapperHelper.getDoubleField(entity, "cost");
	}
}
