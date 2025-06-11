package com.studiop.dormmanagementsystem.entity.dto;

import com.studiop.dormmanagementsystem.entity.Member;
import com.studiop.dormmanagementsystem.entity.enums.PaymentStatus;

public record MemberDto(
        Long id,
        String studentId,
        String name,
        String phone,
        BuildingDto building,
        String roomNumber,
        PaymentStatus paymentStatus
) {
    public static MemberDto fromEntity(Member member) {
        return new MemberDto(
                member.getId(), member.getStudentId(), member.getName(), member.getPhone(), BuildingDto.fromEntity(member.getBuilding()), member.getRoomNumber(), member.getPaymentStatus()
        );
    }
}