package com.studiop.dormmanagementsystem.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class RoundRequest {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String password;
}
