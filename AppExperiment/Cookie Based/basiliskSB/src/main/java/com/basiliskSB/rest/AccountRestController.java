package com.basiliskSB.rest;
import com.basiliskSB.dto.account.ChangePasswordDTO;
import com.basiliskSB.dto.account.RegisterDTO;
import com.basiliskSB.dto.account.RequestTokenDTO;
import com.basiliskSB.dto.account.UserIdentityDTO;
import com.basiliskSB.dto.utility.Dropdown;
import com.basiliskSB.dto.utility.DropdownDTO;
import com.basiliskSB.service.abstraction.AccountService;
import com.basiliskSB.component.JwtManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@CrossOrigin(
    origins = "http://localhost:8080",
    allowCredentials = "true"
)
@RestController
@RequestMapping("/api/account")
public class AccountRestController extends AbstractRestController{

    @Autowired
    private AccountService service;

    @Autowired
    private JwtManager jwtManager;

    @Operation(
        summary = "Digunakan untuk me-request JWT (Token).",
        description = "Request ini akan mengembalikan username, role dan token."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = String.class )
        )}
    )
    @PostMapping("/authenticate")
    public ResponseEntity<Object> authenticate(@Valid @RequestBody RequestTokenDTO dto, BindingResult bindingResult, HttpServletResponse response){
        if(!bindingResult.hasErrors()) {
            if(service.checkPassword(dto.getUsername(), dto.getPassword())){
                var role = service.getAccountRole(dto.getUsername());
                var token = jwtManager.generateToken(dto.getSubject(),dto.getUsername(), role, dto.getAudience());
                var cookieValue = ResponseCookie.from("access_token", token).httpOnly(true).secure(false).path("/").maxAge(Duration.ofHours(1)).sameSite("Lax").build();
                response.addHeader(HttpHeaders.SET_COOKIE, cookieValue.toString());
                var responseDTO = new UserIdentityDTO(dto.getUsername(), role);
                return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
            }
            return ResponseEntity.status(401).body("Username and password are not found.");
        }
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(getErrors(bindingResult.getAllErrors()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logout(HttpServletResponse response){
        var cookieValue = ResponseCookie.from("access_token", "").httpOnly(true).secure(false).path("/").maxAge(0).sameSite("Lax").build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookieValue.toString());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(
        summary = "Mendapatkan macam-maca role dropdown.",
        description = "Digunakan pada register atau sign up form."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = DropdownDTO.class )
        )}
    )
    @GetMapping("/roleDropdown")
    public ResponseEntity<Object> getRoleDropdown(Model model) {
        var roleDropdown = Dropdown.getRoleDropdown();
        return ResponseEntity.status(HttpStatus.OK).body(roleDropdown);
    }

    @Operation(
        summary = "Digunakan untuk me-register new user.",
        description = "Request ini bisa digunakan untuk sign up form."
    )
    @ApiResponse(
        responseCode = "201"
    )
    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterDTO dto, BindingResult bindingResult) {
        if(!bindingResult.hasErrors()) {
            service.registerAccount(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Register successful");
        }
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(getErrors(bindingResult.getAllErrors()));
    }

    @Operation(
        summary = "Digunakan untuk mengganti password existing user.",
        description = "Request ini bisa digunakan pada form change password."
    )
    @ApiResponse(
        responseCode = "204"
    )
    @PatchMapping("/changePassword")
    public ResponseEntity<Object> changePassword(@Valid @RequestBody ChangePasswordDTO dto, BindingResult bindingResult){
        if(!bindingResult.hasErrors()) {
            service.changePassword(dto);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Change Password Success");
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(getErrors(bindingResult.getAllErrors()));
        }
    }
}
