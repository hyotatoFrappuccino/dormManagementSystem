package com.studiop.dormmanagementsystem.entity.dto;

import com.studiop.dormmanagementsystem.entity.enums.PaymentStatus;
import com.studiop.dormmanagementsystem.entity.enums.PaymentType;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PaymentUpdateRequest {
    private String name;
    private Integer amount;
    private LocalDate date;
    private PaymentType type;
    private PaymentStatus status;
}
