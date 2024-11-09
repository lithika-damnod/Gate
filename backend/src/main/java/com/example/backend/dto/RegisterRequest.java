package com.example.backend.dto;

import com.example.backend.models.Account;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @Valid

    @NotNull(message = "Email is required") @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    String email;

    @NotNull(message = "Password is required") @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must contain at least 8 characters")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Password must include at least one uppercase letter, one lowercase letter, one digit, and one special character."
    )
    private String password;

    @NotNull(message = "First Name is required") @NotBlank(message = "First Name is required")
    @Size(max = 50, message = "First name must be less than 50 characters.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "First name can only contain alphabetic characters, with no spaces or special characters.")
    private String firstName;

    @NotNull(message = "Last Name is required") @NotBlank(message = "Last Name is required")
    @Size(max = 50, message = "Last name must be less than 50 characters.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Last name can only contain alphabetic characters, with no spaces or special characters.")
    private String lastName;


    public Account map(String password) {
        return new Account(null, this.email, this.firstName, this.lastName, password);
    }
}
