package com.example.spring_security.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank
        String name,
        @DecimalMin("0.01")
        BigDecimal price
) {
}
