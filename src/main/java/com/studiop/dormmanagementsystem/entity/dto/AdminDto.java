package com.studiop.dormmanagementsystem.entity.dto;

import com.studiop.dormmanagementsystem.entity.Admin;
import com.studiop.dormmanagementsystem.entity.enums.Role;

import java.time.LocalDate;

public record AdminDto(
        Long id,
        String name,
        String email,
        Role role,
        LocalDate creationDate
) {
    public static AdminDto fromEntity(Admin admin) {
        return new AdminDto(
                admin.getId(), admin.getName(), admin.getEmail(), admin.getRole(), admin.getCreatedDate().toLocalDate()
        );
    }
}