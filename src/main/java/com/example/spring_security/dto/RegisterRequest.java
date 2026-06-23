package com.example.spring_security.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(

        @NotBlank
        String username,
        @NotBlank
        String name,
        @NotBlank
        String password

) {
}
