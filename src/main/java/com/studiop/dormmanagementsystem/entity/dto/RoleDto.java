package com.studiop.dormmanagementsystem.entity.dto;

import com.studiop.dormmanagementsystem.entity.enums.Role;

public record RoleDto(
        String key,
        String title
) {
    public static RoleDto fromEntity(Role role) {
        return new RoleDto(
                role.getKey(), role.getTitle()
        );
    }
}