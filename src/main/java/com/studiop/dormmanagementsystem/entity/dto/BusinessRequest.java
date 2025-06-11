package com.studiop.dormmanagementsystem.entity.dto;

import jakarta.validation.constraints.NotBlank;

public record BusinessRequest(
        @NotBlank
        String name
) {
}