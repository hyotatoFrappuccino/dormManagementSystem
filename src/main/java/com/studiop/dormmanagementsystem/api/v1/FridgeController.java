package com.studiop.dormmanagementsystem.api.v1;

import com.studiop.dormmanagementsystem.entity.dto.FridgeApplyRequest;
import com.studiop.dormmanagementsystem.entity.dto.FridgeMemberInfoResponse;
import com.studiop.dormmanagementsystem.service.FridgeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fridge")
@RequiredArgsConstructor
public class FridgeController {

    private final FridgeService fridgeService;

    @Operation(summary = "냉장고 신청 관련 학생 정보 조회")
    @GetMapping()
    public ResponseEntity<FridgeMemberInfoResponse> getMember(@RequestParam String studentId) {
        FridgeMemberInfoResponse response = new FridgeMemberInfoResponse(
                fridgeService.getMemberInfo(studentId),
                fridgeService.getFridgeByMemberInfo(studentId)
        );
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "냉장고 신청")
    @PostMapping()
    public ResponseEntity<Void> apply(@RequestBody FridgeApplyRequest request) {
        fridgeService.apply(request);
        return ResponseEntity.ok().build();
    }
}
