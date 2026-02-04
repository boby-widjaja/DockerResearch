package com.basiliskSB.dto.region;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Region setelah di assign salesman..")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AssignSalesmanResponseDTO {
    @Schema(description = "Region PK.")
    private Long id;
    @Schema(description = "Nama kota dari region.")
    private String city;
    @Schema(description = "Keterangan dari region ini.")
    private String remark;
    @Schema(description = "Jumlah salesman saat ini.")
    private Integer totalSalesman;
}
