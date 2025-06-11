package com.studiop.dormmanagementsystem.entity.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record RoundRequest(
        @NotBlank
        String name,
        @NotBlank
        LocalDate startDate,
        @NotBlank
        LocalDate endDate,
        @NotBlank
        String password
) {
}