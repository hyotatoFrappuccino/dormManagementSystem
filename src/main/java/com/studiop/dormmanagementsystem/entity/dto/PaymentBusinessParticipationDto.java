package com.studiop.dormmanagementsystem.entity.dto;

import com.studiop.dormmanagementsystem.entity.PaymentBusinessParticipation;

public record PaymentBusinessParticipationDto(
        Long id,
        Long businessId
) {
    public static PaymentBusinessParticipationDto fromEntity(PaymentBusinessParticipation e) {
        return new PaymentBusinessParticipationDto(
                e.getId(), e.getBusiness().getId()
        );
    }
}