package com.studiop.dormmanagementsystem.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberInfoDto {
    private String name;
    private Boolean isPaid;
    private Boolean isAgreed;
    private String building;
    private String roomNumber;
}