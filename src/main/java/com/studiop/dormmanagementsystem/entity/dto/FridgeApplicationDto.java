package com.studiop.dormmanagementsystem.entity.dto;

import com.studiop.dormmanagementsystem.entity.FridgeApplication;
import com.studiop.dormmanagementsystem.entity.enums.FridgeType;

import java.time.LocalDateTime;

public record FridgeApplicationDto(
        Long id,
        MemberDto member,
        BuildingDto building,
        RoundDto round,
        FridgeType type,
        LocalDateTime appliedAt
) {
    public static FridgeApplicationDto fromEntity(FridgeApplication application) {
        return new FridgeApplicationDto(
                application.getId(),
                MemberDto.fromEntity(application.getMember()),
                BuildingDto.fromEntity(application.getBuilding()),
                RoundDto.fromEntity(application.getRound()),
                application.getType(),
                application.getAppliedAt()
        );
    }
}