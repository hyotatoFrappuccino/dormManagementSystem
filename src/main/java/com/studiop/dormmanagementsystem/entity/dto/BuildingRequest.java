package com.studiop.dormmanagementsystem.entity.dto;

import com.studiop.dormmanagementsystem.entity.enums.FridgeType;
import com.studiop.dormmanagementsystem.validation.EnumValue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record BuildingRequest(
        @NotBlank
        String name,
        @Min(0)
        int fridgeSlots,
        @Min(0)
        int freezerSlots,
        @Min(0)
        int integratedSlots,
        @EnumValue(enumClass = FridgeType.class, message = "존재하지 않는 유형입니다.")
        String type
) {
}