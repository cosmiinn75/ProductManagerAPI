package com.example.spring_security.dto;

public record UserResponse(
        Long id,
        String username,
        String name,
        String role
) {
}
