package com.studiop.dormmanagementsystem.entity.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class RoundUpdateRequest {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
}
