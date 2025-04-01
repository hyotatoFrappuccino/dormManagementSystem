package com.studiop.dormmanagementsystem.entity.dto;

import com.studiop.dormmanagementsystem.entity.Admin;
import com.studiop.dormmanagementsystem.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminDto {
    private Long id;
    private String name;
    private String email;
    private Role role;

    public static AdminDto fromEntity(Admin admin) {
        return new AdminDto(
                admin.getId(), admin.getName(), admin.getEmail(), admin.getRole()
        );
    }
}
