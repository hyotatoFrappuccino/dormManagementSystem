package com.studiop.dormmanagementsystem.api.v1;

import com.studiop.dormmanagementsystem.service.AppConfigService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/config")
@RequiredArgsConstructor
public class AppConfigController {

    private final AppConfigService appConfigService;

    @Operation(summary = "설정값 조회")
    @GetMapping("/{key}")
    public ResponseEntity<String> getConfig(@PathVariable String key) {
        return ResponseEntity.ok(appConfigService.getConfigValue(key, ""));
    }

    @Operation(summary = "설정값 저장/변경")
    @PostMapping("/{key}")
    public ResponseEntity<Void> setConfig(@PathVariable String key, @RequestBody String value) {
        appConfigService.setConfigValue(key, value);
        return ResponseEntity.ok().build();
    }
}