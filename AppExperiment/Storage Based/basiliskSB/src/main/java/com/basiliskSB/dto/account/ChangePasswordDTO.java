package com.basiliskSB.dto.account;
import com.basiliskSB.validation.Authentication;
import com.basiliskSB.validation.Compare;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Compare(message="Password is not matched.", firstField="newPassword", secondField="newPasswordConfirmation")
@Authentication(message = "Username and Password are incorrect.")
public class ChangePasswordDTO {
    private String username;

    @NotBlank(message="Old Password is required")
    @Size(max=20, message="Old Password can't be more than 20 characters.")
    private String oldPassword;

    @NotBlank(message="New Password is required")
    @Size(max=20, message="New Password can't be more than 20 characters.")
    private String newPassword;

    @NotBlank(message="Password confirmation is required")
    @Size(max=20, message="Password confirmation can't be more than 20 characters.")
    private String newPasswordConfirmation;
}
