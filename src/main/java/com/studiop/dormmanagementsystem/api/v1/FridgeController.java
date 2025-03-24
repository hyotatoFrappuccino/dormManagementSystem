package com.studiop.dormmanagementsystem.api.v1;

import com.studiop.dormmanagementsystem.entity.dto.FridgeApplyRequest;
import com.studiop.dormmanagementsystem.service.FridgeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/fridge")
@RequiredArgsConstructor
public class FridgeController {

    private final FridgeService fridgeService;

    @Operation(summary = "냉장고 신청 관련 학생 정보 조회")
    @GetMapping()
    public ResponseEntity<Map<String, Object>> getMember(@RequestParam String studentId) {
        Map<String, Object> response = new HashMap<>();
        response.put("defaultInfo", fridgeService.getMemberInfo(studentId));
        response.put("fridgeApplyInfo", fridgeService.getFridgeInfo(studentId));
        return ResponseEntity.ok(response);

//        return ResponseEntity.ok(fridgeService.getMemberInfo(studentId));
    }

    @GetMapping("test")
    public ResponseEntity<Map<String, Object>> test(@RequestParam String studentId) {
        Map<String, Object> response = new HashMap<>();
        response.put("defaultInfo", fridgeService.getMemberInfo(studentId));
        response.put("fridgeApplyInfo", fridgeService.getFridgeInfo(studentId));
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "냉장고 신청")
    @PostMapping()
    public ResponseEntity<Void> apply(@RequestBody FridgeApplyRequest request) {
        fridgeService.apply(request);
        return ResponseEntity.ok().build();
    }
}
