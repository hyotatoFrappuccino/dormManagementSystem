package com.studiop.dormmanagementsystem.entity.dto;

import com.studiop.dormmanagementsystem.entity.enums.PaymentStatus;

public record MemberInfoDto(
        String name,
        PaymentStatus isPaid,
        Boolean isAgreed,
        String building,
        String roomNumber
) {
}