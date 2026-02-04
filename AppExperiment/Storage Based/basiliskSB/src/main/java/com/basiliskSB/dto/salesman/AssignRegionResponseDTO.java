package com.basiliskSB.dto.salesman;
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
public class AssignRegionResponseDTO {
    @Schema(description = "Employee Number PK.")
    private String employeeNumber;

    @Schema(description = "Nama depan salesman.")
    private String firstName;

    @Schema(description = "Nama belakang salesman.")
    private String lastName;

    @Schema(description = "Jumlah region saat ini.")
    private Integer totalRegion;
}
