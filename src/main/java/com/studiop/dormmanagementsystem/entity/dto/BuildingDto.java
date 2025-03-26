package com.studiop.dormmanagementsystem.entity.dto;

import com.studiop.dormmanagementsystem.entity.Building;
import com.studiop.dormmanagementsystem.entity.enums.FridgeType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BuildingDto {
    private Long id;
    private String name;
    private int fridgeSlots; // 냉장 슬롯
    private int freezerSlots; // 냉동 슬롯
    private int integratedSlots; // 통합 슬롯
    private FridgeType type;

    public static BuildingDto fromEntity(Building building) {
        return new BuildingDto(
                building.getId(), building.getName(), building.getFridgeSlots(), building.getFreezerSlots(), building.getIntegratedSlots(), building.getType()
        );
    }
}