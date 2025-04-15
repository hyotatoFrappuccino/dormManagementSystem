package com.studiop.dormmanagementsystem.api.v1;

import com.studiop.dormmanagementsystem.entity.Building;
import com.studiop.dormmanagementsystem.entity.dto.BuildingDto;
import com.studiop.dormmanagementsystem.service.BuildingService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/buildings")
@RequiredArgsConstructor
public class BuildingController {

    private final BuildingService buildingService;

    @Operation(summary = "건물 전체 목록 반환")
    @GetMapping
    public ResponseEntity<List<BuildingDto>> buildings() {
        List<BuildingDto> buildings = buildingService.getAllBuildings();
        return ResponseEntity.ok(buildings);
    }

    @Operation(summary = "건물 추가")
    @PostMapping
    public ResponseEntity<BuildingDto> addBuilding(@RequestBody BuildingDto request) {
        Building savedBuilding = buildingService.addBuilding(request.getName(), request.getFridgeSlots(), request.getFreezerSlots(), request.getIntegratedSlots(), request.getType());
        URI location = URI.create("/api/v1/buildings/" + savedBuilding.getId());
        return ResponseEntity.created(location).body(BuildingDto.fromEntity(savedBuilding));
    }

    @Operation(summary = "건물 수정")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateBuilding(@PathVariable("id") Long id, @RequestBody BuildingDto request) {
        buildingService.editBuilding(id, request.getName(), request.getFridgeSlots(), request.getFreezerSlots(), request.getIntegratedSlots(), request.getType());
        return ResponseEntity.ok("OK");
    }

    @Operation(summary = "건물 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBuilding(@PathVariable("id") Long id) {
        buildingService.deleteBuilding(id);
        return ResponseEntity.ok("OK");
    }
}