package com.example.backend.dto;

import com.example.backend.models.Account;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NonNull String email;
    @NonNull private String password;
    @NonNull private String firstName;
    @NonNull private String lastName;


    public Account map(String password) {
        return new Account(null, this.email, this.firstName, this.lastName, password);
    }
}
