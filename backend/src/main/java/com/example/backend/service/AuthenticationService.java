package com.example.backend.service;

import com.example.backend.dto.AuthenticationRequest;
import com.example.backend.dto.AuthenticationResponse;
import com.example.backend.dto.RegisterRequest;
import com.example.backend.models.Account;
import com.example.backend.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse register(RegisterRequest request) {

        // TODO: DO SOME CHECKS HERE...
        // save the account
        Account account = request.map(passwordEncoder.encode(request.getPassword()));
        accountRepository.save(account);

        var jwtToken = jwtService.generateToken(account);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        var account = accountRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("Account Not Found")
        );

        var jwtToken = jwtService.generateToken(account);
        System.out.println("new token: " + jwtToken);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public Map<String, ?> info(Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return Map.of("status", "not authenticated");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Account account = accountRepository.findByEmail(userDetails.getUsername()).orElse(null);

        if (account == null) {
            return Map.of("status", "account not found");
        }


        Map<String, String> subInfo = new LinkedHashMap<>() {{
            put("email", account.getEmail());
            put("first_name", account.getFirstName());
            put("last_name", account.getLastName());
        }};


        return (Map<String, ?>) new LinkedHashMap<String, Object>() {{
            put("status", "authenticated");
            put("info", subInfo);
        }};
    }
}
