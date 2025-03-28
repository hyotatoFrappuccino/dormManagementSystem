package com.studiop.dormmanagementsystem.entity.dto;

import com.studiop.dormmanagementsystem.entity.enums.FridgeType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FridgeApplyRequest {
    private Long roundId;
    private String studentId;
    private FridgeType type;
}
