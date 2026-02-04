package com.basiliskSB.dto.category;
import jakarta.validation.constraints.*;

import com.basiliskSB.utility.MapperHelper;
import com.basiliskSB.validation.UniqueCategoryName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "Data form Category yang digunakan untuk insert dan update.")
@NoArgsConstructor
@AllArgsConstructor
@UniqueCategoryName(message="Category with this name is already exist.")
@Getter
@Setter
public class UpsertCategoryDTO {

	@Schema(description = "PK Category.")
	private Long id;

	@Schema(description = "Nama category maximum 50 characters.")
	@NotBlank(message="Category name is required")
	@Size(max=50, message="Category name can't be more than 50 characters.")
	private String name;

	@Schema(description = "Description maximum 500 characters.")
	@Size(max=500, message="Category description can't be more than 500 characters.")
	private String description;

	public UpsertCategoryDTO(Object entity) {
		id = MapperHelper.getLongField(entity, "id");
		name = MapperHelper.getStringField(entity, "name");
		description = MapperHelper.getStringField(entity, "description");
	}
}
