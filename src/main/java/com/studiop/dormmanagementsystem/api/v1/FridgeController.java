package com.studiop.dormmanagementsystem.api.v1;

import com.studiop.dormmanagementsystem.service.FridgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/fridge")
@RequiredArgsConstructor
public class FridgeController {

    private final FridgeService fridgeService;

    @GetMapping("/member")
    public ResponseEntity<Map<String, Object>> getMember(@RequestParam String studentId) {
        return ResponseEntity.ok(fridgeService.getMemberInfo(studentId));
    }
}
