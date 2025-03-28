package com.studiop.dormmanagementsystem.api.v1;

import com.studiop.dormmanagementsystem.entity.dto.FridgeApplicationDto;
import com.studiop.dormmanagementsystem.entity.dto.FridgeApplyRequest;
import com.studiop.dormmanagementsystem.entity.dto.FridgeMemberInfoResponse;
import com.studiop.dormmanagementsystem.service.FridgeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fridge")
@RequiredArgsConstructor
public class FridgeController {

    private final FridgeService fridgeService;

    @Operation(summary = "냉장고 신청 관련 학생 정보 조회")
    @GetMapping("/{studentId}")
    public ResponseEntity<FridgeMemberInfoResponse> getMember(@PathVariable String studentId) {
        FridgeMemberInfoResponse response = new FridgeMemberInfoResponse(
                fridgeService.getMemberInfo(studentId),
                fridgeService.getFridgeByMemberInfo(studentId)
        );
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "냉장고 신청 목록 전체 조회")
    @GetMapping
    public ResponseEntity<List<FridgeApplicationDto>> getAllFridgeApplications() {
        return ResponseEntity.ok(fridgeService.getAllFridgeApplications().stream().map(FridgeApplicationDto::fromEntity).toList());
    }

    @Operation(summary = "냉장고 신청")
    @PostMapping
    public ResponseEntity<Void> apply(@RequestBody FridgeApplyRequest request) {
        fridgeService.apply(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "냉장고 신청 취소")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        fridgeService.deleteFridgeApplication(id);
        return ResponseEntity.noContent().build();
    }
}
