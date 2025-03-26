package com.studiop.dormmanagementsystem.entity.dto;

import com.studiop.dormmanagementsystem.entity.Member;
import com.studiop.dormmanagementsystem.entity.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberDto {
    private Long id;
    private String studentId;
    private String name;
    private String phone;
    private BuildingDto building;
    private String roomNumber;
    private PaymentStatus paymentStatus;

    public static MemberDto fromEntity(Member member) {
        return new MemberDto(
                member.getId(), member.getStudentId(), member.getName(), member.getPhone(), BuildingDto.fromEntity(member.getBuilding()), member.getRoomNumber(), member.getPaymentStatus()
        );
    }
}
