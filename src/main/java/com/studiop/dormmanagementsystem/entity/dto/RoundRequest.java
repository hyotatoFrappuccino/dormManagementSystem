package com.studiop.dormmanagementsystem.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record RoundRequest(
        @NotBlank
        String name,
        @NotNull
        LocalDate startDate,
        @NotNull
        LocalDate endDate,
        @NotBlank
        String password
) {
}