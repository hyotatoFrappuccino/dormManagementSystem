package com.studiop.dormmanagementsystem.entity.dto;

import com.studiop.dormmanagementsystem.entity.Round;

import java.time.LocalDate;

public record RoundDto(
        Long id,
        String name,
        LocalDate startDate,
        LocalDate endDate,
        String password
) {
    public static RoundDto fromEntity(Round round) {
        return new RoundDto(
                round.getId(), round.getName(), round.getStartDate(), round.getEndDate(), round.getPassword()
        );
    }
}