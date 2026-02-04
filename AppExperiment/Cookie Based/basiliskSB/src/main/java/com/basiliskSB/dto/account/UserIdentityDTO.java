package com.basiliskSB.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Data object yang digunakan untuk me-request JWT.")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserIdentityDTO {
    @Schema(description = "Username yang digunakan untuk aplikasi client-side.")
    private String username;

    @Schema(description = "Role yang digunakan untuk aplikasi client-side.")
    private String role;
}
