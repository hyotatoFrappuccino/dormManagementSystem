package com.studiop.dormmanagementsystem.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FridgeMemberInfoResponse {
    private MemberInfoDto defaultInfo;
    private List<FridgeApplicationDto> fridgeApplyInfo;
}
