package com.studiop.dormmanagementsystem.entity.dto;

import com.studiop.dormmanagementsystem.entity.Building;
import com.studiop.dormmanagementsystem.entity.enums.FridgeType;

public record BuildingDto(
        Long id,
        String name,
        int fridgeSlots, // 냉장 슬롯
        int freezerSlots, // 냉동 슬롯
        int integratedSlots, // 통합 슬롯
        FridgeType type
) {
    public static BuildingDto fromEntity(Building building) {
        return new BuildingDto(
                building.getId(), building.getName(), building.getFridgeSlots(), building.getFreezerSlots(), building.getIntegratedSlots(), building.getType()
        );
    }
}