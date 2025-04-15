package com.studiop.dormmanagementsystem.entity.dto;

import com.studiop.dormmanagementsystem.entity.Payment;
import com.studiop.dormmanagementsystem.entity.enums.PaymentStatus;
import com.studiop.dormmanagementsystem.entity.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class PaymentDto {

    private Long id;
    private String name;
    private Integer amount;
    private LocalDate date;
    private PaymentStatus status;
    private PaymentType type;
    private List<PaymentBusinessParticipationDto> businessParticipations;

    public static PaymentDto fromEntity(Payment payment) {
        return new PaymentDto(
                payment.getId(), payment.getStudentId(), payment.getAmount(), payment.getDate(), payment.getStatus(), payment.getType(), payment.getParticipations().stream().map(PaymentBusinessParticipationDto::fromEntity).toList()
        );
    }
}