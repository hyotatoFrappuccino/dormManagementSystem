package com.studiop.dormmanagementsystem.api.v1;

import com.studiop.dormmanagementsystem.entity.Building;
import com.studiop.dormmanagementsystem.entity.dto.BuildingDto;
import com.studiop.dormmanagementsystem.service.BuildingService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/buildings")
@RequiredArgsConstructor
public class BuildingController {

    private final BuildingService buildingService;

    @Operation(summary = "건물 전체 목록 반환")
    @GetMapping
    public ResponseEntity<List<Building>> buildings() {
        List<Building> buildings = buildingService.getAllBuildings();
        return ResponseEntity.ok(buildings);
    }

    @Operation(summary = "건물 추가")
    @PostMapping
    public void addBuilding(@RequestBody BuildingDto request) {
        buildingService.addBuilding(request.getName(), request.getTotalSlots());
    }

    @Operation(summary = "건물 수정")
    @PutMapping("/{id}")
    public ResponseEntity<String> editBuilding(
            @PathVariable("id") Long id,
            @RequestBody BuildingDto request) {
        buildingService.editBuilding(id, request.getName(), request.getTotalSlots());

        return ResponseEntity.ok("OK");
    }

    @Operation(summary = "건물 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBuilding(@PathVariable("id") Long id) {
        buildingService.deleteBuilding(id);
        return ResponseEntity.ok("OK");
    }
}
