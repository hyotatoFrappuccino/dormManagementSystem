package com.studiop.dormmanagementsystem.entity.dto;

import com.studiop.dormmanagementsystem.entity.FridgeApplication;
import com.studiop.dormmanagementsystem.entity.enums.FridgeType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class FridgeApplicationDto {

    private Long id;
    private MemberDto member;
    private BuildingDto building;
    private RoundDto round;
    private FridgeType type;
    private LocalDateTime appliedAt;

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