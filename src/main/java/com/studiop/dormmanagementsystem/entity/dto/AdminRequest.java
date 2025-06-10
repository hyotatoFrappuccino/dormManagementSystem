package com.studiop.dormmanagementsystem.entity.dto;

import com.studiop.dormmanagementsystem.entity.enums.Role;
import com.studiop.dormmanagementsystem.validation.EnumValue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AdminRequest(
        @NotBlank
        String name,
        @NotBlank
        @Email
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@gmail\\.com$", message = "@gmail.com 이메일만 허용됩니다.")
        String email,
        @EnumValue(enumClass = Role.class, message = "존재하지 않는 역할입니다.")
        String role
) {
}