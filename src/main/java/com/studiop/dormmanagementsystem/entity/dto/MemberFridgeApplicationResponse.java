package com.studiop.dormmanagementsystem.entity.dto;

import com.studiop.dormmanagementsystem.entity.Member;

import java.util.List;

public record MemberFridgeApplicationResponse(
        Long id,
        String studentId,
        String name,
        String phone,
        String buildingName,
        String roomNumber,
        int warningCount,
        List<FridgeApplicationInfo> fridgeApplications
) {
    public static MemberFridgeApplicationResponse fromEntity(Member member) {
        List<FridgeApplicationInfo> applications = member.getFridgeApplications().stream()
                .map(FridgeApplicationInfo::fromEntity)
                .toList();

        return new MemberFridgeApplicationResponse(
                member.getId(),
                member.getStudentId(),
                member.getName(),
                member.getPhone(),
                member.getBuilding().getName(),
                member.getRoomNumber(),
                member.getWarningCount(),
                applications
        );
    }
}