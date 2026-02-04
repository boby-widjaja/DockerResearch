package com.basiliskSB.dto.delivery;
import com.basiliskSB.utility.MapperHelper;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data Delivery yang akan ditunjukan di index grid.")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DeliveryGridDTO {

	@Schema(description = "Delivery PK")
	private Long id;

	@Schema(description = "Nama perusahaan pengiriman.")
	private String companyName;

	@Schema(description = "Nomor telpon perusahaan pengiriman.")
	private String phone;

	@Schema(description = "Ongkos kirim perusahaan.")
	private Double cost;

	public DeliveryGridDTO(Object row) {
		id = MapperHelper.getLongField(row, 0);
		companyName = MapperHelper.getStringField(row, 1);
		phone = MapperHelper.getStringField(row, 2);
		cost = MapperHelper.getDoubleField(row, 3);
	}
}
