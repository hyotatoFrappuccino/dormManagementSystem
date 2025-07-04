package com.studiop.dormmanagementsystem.api.v1;

import com.studiop.dormmanagementsystem.entity.dto.FridgeApplyRequest;
import com.studiop.dormmanagementsystem.entity.dto.FridgeMemberInfoResponse;
import com.studiop.dormmanagementsystem.entity.dto.MemberFridgeApplicationResponse;
import com.studiop.dormmanagementsystem.service.FridgeService;
import com.studiop.dormmanagementsystem.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fridge")
@RequiredArgsConstructor
public class FridgeController {

    private final FridgeService fridgeService;
    private final MemberService memberService;

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
    public ResponseEntity<List<MemberFridgeApplicationResponse>> getAllMemberFridgeApplications() {
        List<MemberFridgeApplicationResponse> response = memberService.getAllMemberWithApplications();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "냉장고 신청")
    @PostMapping
    public ResponseEntity<Void> apply(final @RequestBody @Valid FridgeApplyRequest request) {
        fridgeService.apply(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "냉장고 신청 목록 전체 삭제")
    @PreAuthorize("hasRole('PRESIDENT')")
    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        fridgeService.deleteAllFridgeApplication();
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "냉장고 신청 취소")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        fridgeService.deleteFridgeApplication(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "경고 조회")
    @GetMapping("/{id}/warningCount")
    public ResponseEntity<Integer> getWarningCount(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getWarningCount(id));
    }

    @Operation(summary = "경고 추가")
    @PostMapping("/{id}/warningCount/increase")
    public ResponseEntity<Void> increaseWarningCount(@PathVariable Long id) {
        memberService.increaseWarning(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "경고 감소")
    @PostMapping("/{id}/warningCount/decrease")
    public ResponseEntity<Void> decreaseWarningCount(@PathVariable Long id) {
        memberService.decreaseWarning(id);
        return ResponseEntity.noContent().build();
    }
}
