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

    @NotBlank(message = "email is required")
    @Email(message = "email must be valid")
    String email;

    @NotBlank(message = "password is required")
    @Size(min = 8, message = "password must contain at least 8 characters")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "password must include at least one uppercase letter, one lowercase letter, one digit, and one special character."
    )
    private String password;

    @NotBlank(message = "first_name is required")
    @Size(max = 50, message = "first_name must be less than 50 characters.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "first_name can only contain alphabetic characters, with no spaces or special characters.")
    private String firstName;

    @NotBlank(message = "last_name is required")
    @Size(max = 50, message = "last_name must be less than 50 characters.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "last_name can only contain alphabetic characters, with no spaces or special characters.")
    private String lastName;


    public Account map(String password) {
        return new Account(null, this.email, this.firstName, this.lastName, password);
    }
}
