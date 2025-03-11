package com.studiop.dormmanagementsystem.controller;

import com.studiop.dormmanagementsystem.entity.Payment;
import com.studiop.dormmanagementsystem.entity.PaymentType;
import com.studiop.dormmanagementsystem.entity.dto.BuildingDto;
import com.studiop.dormmanagementsystem.service.BuildingService;
import com.studiop.dormmanagementsystem.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class Dashboard {

    private final BuildingService buildingService;
    private final PaymentService paymentService;

    /**
     * 대시보드
     * @return 건물별 이용자 수
     */
    @GetMapping("/dashboard")
    @ResponseBody
    public Map<String, Object> dashboard() {

        List<BuildingDto> buildings = buildingService.getAllBuildings();
        long totalPayers = paymentService.getTotalPayers();
        int totalUsers = buildings.stream().mapToInt(BuildingDto::getTotalUsers).sum();

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
    @ResponseBody
    public Map<String, Object> buildings() {
        List<BuildingDto> buildings = buildingService.getAllBuildings();

        Map<String, Object> response = new HashMap<>();
        response.put("buildings", buildings.stream()
                .map(BuildingDto::getName)
                .collect(Collectors.toList()));

        return response;
    }

}
