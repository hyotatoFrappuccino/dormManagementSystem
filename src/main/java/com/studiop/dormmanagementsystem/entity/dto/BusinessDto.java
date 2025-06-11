package com.studiop.dormmanagementsystem.entity.dto;

import com.studiop.dormmanagementsystem.entity.Business;

public record BusinessDto(
        Long id,
        String name
) {
    public static BusinessDto fromEntity(Business business) {
        return new BusinessDto(
                business.getId(), business.getName()
        );
    }
}