package com.studiop.dormmanagementsystem.api.v1;

import com.studiop.dormmanagementsystem.entity.Building;
import com.studiop.dormmanagementsystem.entity.dto.BuildingDto;
import com.studiop.dormmanagementsystem.service.BuildingService;
import com.studiop.dormmanagementsystem.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class Dashboard {

    private final BuildingService buildingService;
    private final PaymentService paymentService;

    /**
     * 대시보드
     * @return 건물별 이용자 수
     */
    @GetMapping("/dashboard")
    public Map<String, Object> dashboard() {

        List<Building> buildings = buildingService.getAllBuildings();
        long totalPayers = paymentService.getTotalPayers();
        int totalUsers = buildings.stream().mapToInt(Building::getTotalUsers).sum();

        Map<String, Object> response = new HashMap<>();
        response.put("totalPayers", totalPayers);
        response.put("totalUsers", totalUsers);
        response.put("buildings", buildings);

        return response;
    }

    /**
     * @return 건물 목록
     */
    @GetMapping("/buildings")
    public Map<String, Object> buildings() {
        List<Building> buildings = buildingService.getAllBuildings();

        Map<String, Object> response = new HashMap<>();
        response.put("buildings", buildings.stream()
                .map(Building::getName)
                .collect(Collectors.toList()));

        return response;
    }

}
