package com.studiop.dormmanagementsystem.entity.dto;

import com.studiop.dormmanagementsystem.entity.Building;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BuildingDto {
    private String name;
    private int totalSlots;

    public static BuildingDto fromEntity(Building building) {
        return new BuildingDto(
                building.getName(), building.getTotalSlots()
        );
    }
}
