package com.studiop.dormmanagementsystem.entity.dto;

import com.studiop.dormmanagementsystem.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoleDto {

    private String key;
    private String title;

    public static RoleDto fromEntity(Role role) {
        return new RoleDto(
                role.getKey(), role.getTitle()
        );
    }
}