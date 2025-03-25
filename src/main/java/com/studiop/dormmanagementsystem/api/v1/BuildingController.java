package com.studiop.dormmanagementsystem.api.v1;

import com.studiop.dormmanagementsystem.entity.Building;
import com.studiop.dormmanagementsystem.entity.dto.BuildingDto;
import com.studiop.dormmanagementsystem.service.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BuildingController {

    private final BuildingService buildingService;

    /**
     * @return 건물 목록
     */
    @GetMapping("/buildings")
    public ResponseEntity<List<Building>> buildings() {
        List<Building> buildings = buildingService.getAllBuildings();
        return ResponseEntity.ok(buildings);
    }

    @PostMapping("/building")
    public void addBuilding(@RequestBody BuildingDto request) {
        buildingService.addBuilding(request.getName(), request.getTotalSlots());
    }

    @PutMapping("/building")
    public ResponseEntity<String> editBuilding(
            @RequestParam Long id,
            @RequestBody BuildingDto request) {
        buildingService.editBuilding(id, request.getName(), request.getTotalSlots());

        return ResponseEntity.ok("OK");
    }

    @DeleteMapping("/building")
    public ResponseEntity<String> deleteBuilding(@RequestParam Long id) {
        buildingService.deleteBuilding(id);
        return ResponseEntity.ok("OK");
    }
}
