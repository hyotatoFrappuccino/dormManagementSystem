package com.studiop.dormmanagementsystem.entity.dto;

import com.studiop.dormmanagementsystem.entity.PaymentBusinessParticipation;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentBusinessParticipationDto {

    private Long id;
    private Long businessId;

    public static PaymentBusinessParticipationDto fromEntity(PaymentBusinessParticipation e) {
        return new PaymentBusinessParticipationDto(
                e.getId(), e.getBusiness().getId()
        );
    }
}