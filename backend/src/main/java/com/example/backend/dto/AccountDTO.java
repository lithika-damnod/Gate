package com.example.backend.dto;

public record AccountDTO (
        Integer id,
        String email,
        String firstName,
        String lastName
) {}
