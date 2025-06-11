package com.studiop.dormmanagementsystem.entity.dto;

import com.studiop.dormmanagementsystem.entity.Payment;
import com.studiop.dormmanagementsystem.entity.enums.PaymentStatus;
import com.studiop.dormmanagementsystem.entity.enums.PaymentType;

import java.time.LocalDate;
import java.util.List;

public record PaymentDto(
        Long id,
        String name,
        Integer amount,
        LocalDate date,
        PaymentStatus status,
        PaymentType type,
        List<PaymentBusinessParticipationDto> businessParticipations
) {
    public static PaymentDto fromEntity(Payment payment) {
        return new PaymentDto(
                payment.getId(), payment.getStudentId(), payment.getAmount(), payment.getDate(), payment.getStatus(), payment.getType(), payment.getParticipations().stream().map(PaymentBusinessParticipationDto::fromEntity).toList()
        );
    }
}