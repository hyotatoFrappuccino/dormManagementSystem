package com.studiop.dormmanagementsystem.entity.dto;

import java.util.List;

public record FridgeMemberInfoResponse(
        MemberInfoDto defaultInfo,
        List<FridgeApplicationDto> fridgeApplyInfo
) {
}
