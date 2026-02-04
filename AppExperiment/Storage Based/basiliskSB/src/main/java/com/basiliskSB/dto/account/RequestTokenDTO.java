package com.basiliskSB.dto.account;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Schema(description = "Data object yang digunakan untuk me-request JWT.")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class RequestTokenDTO {

    @Schema(description = "Username maximum 20 characters.")
    @NotBlank(message="Username is required")
    private String username;

    @Schema(description = "Password maximum 20 characters.")
    @NotBlank(message="Password is required")
    private String password;

    @Schema(description = "Username, Email atau topic dari requester.")
    private String subject;

    @Schema(description = "Pengguna API")
    private String audience;
}
