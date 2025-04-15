package com.studiop.dormmanagementsystem.entity.dto;

import com.studiop.dormmanagementsystem.entity.Business;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BusinessDto {

    private Long id;
    private String name;

    public static BusinessDto fromEntity(Business business) {
        return new BusinessDto(
                business.getId(), business.getName()
        );
    }
}