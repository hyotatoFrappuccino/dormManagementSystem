package com.studiop.dormmanagementsystem.api.v1;

import com.studiop.dormmanagementsystem.service.AppConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/config")
@RequiredArgsConstructor
public class AppConfigController {

    private final AppConfigService appConfigService;

    @GetMapping("/{key}")
    public ResponseEntity<String> getConfig(@PathVariable String key) {
        return ResponseEntity.ok(appConfigService.getConfigValue(key, ""));
    }

    @PostMapping("/{key}")
    public ResponseEntity<String> setConfig(@PathVariable String key, @RequestBody String value) {
        appConfigService.setConfigValue(key, value);
        return ResponseEntity.ok("설정값이 저장되었습니다.");
    }

}
