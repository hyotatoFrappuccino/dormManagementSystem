package com.studiop.dormmanagementsystem.entity.dto;

import com.studiop.dormmanagementsystem.entity.enums.FridgeType;
import com.studiop.dormmanagementsystem.validation.EnumValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FridgeApplyRequest(
        @NotNull
        Long roundId,
        @NotBlank
        String studentId,
        @EnumValue(enumClass = FridgeType.class, message = "존재하지 않는 유형입니다.")
        String type
) {
}