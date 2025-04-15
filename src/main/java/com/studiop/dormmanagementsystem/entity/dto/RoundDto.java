package com.studiop.dormmanagementsystem.entity.dto;

import com.studiop.dormmanagementsystem.entity.Round;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class RoundDto {

    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String password;

    public static RoundDto fromEntity(Round round) {
        return new RoundDto(
                round.getId(), round.getName(), round.getStartDate(), round.getEndDate(), round.getPassword()
        );
    }
}