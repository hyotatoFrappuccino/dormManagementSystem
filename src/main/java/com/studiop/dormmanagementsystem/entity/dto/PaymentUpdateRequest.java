package com.studiop.dormmanagementsystem.entity.dto;

import com.studiop.dormmanagementsystem.entity.enums.PaymentStatus;
import com.studiop.dormmanagementsystem.entity.enums.PaymentType;
import com.studiop.dormmanagementsystem.validation.EnumValue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PaymentUpdateRequest(
        @NotBlank
        String name,
        @Min(0)
        Integer amount,
        @NotNull
        LocalDate date,
        @EnumValue(enumClass = PaymentStatus.class, message = "존재하지 않는 유형입니다.")
        String status,
        @EnumValue(enumClass = PaymentType.class, message = "존재하지 않는 유형입니다.")
        String type
) {
}