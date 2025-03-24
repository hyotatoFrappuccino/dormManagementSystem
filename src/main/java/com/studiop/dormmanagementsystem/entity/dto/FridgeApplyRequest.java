package com.studiop.dormmanagementsystem.entity.dto;

import com.studiop.dormmanagementsystem.entity.enums.FridgeType;
import lombok.Getter;

@Getter
public class FridgeApplyRequest {
    private Long roundId;
    private String studentId;
    private FridgeType type;
}
