package com.studiop.dormmanagementsystem.controller;

import com.studiop.dormmanagementsystem.dto.BuildingDto;
import com.studiop.dormmanagementsystem.service.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class Dashboard {

    private final BuildingService buildingService;

    @GetMapping("/dashboard")
    @ResponseBody
    public Map<String, Object> dashboard() {

        List<BuildingDto> buildings = buildingService.getAllBuildings();
        int totalPayers = 300; //todo mock data -> 납부자 명단에서 추출
        int totalUsers = buildings.stream().mapToInt(BuildingDto::getTotalUsers).sum();

        Map<String, Object> response = new HashMap<>();
        response.put("totalPayers", totalPayers);
        response.put("totalUsers", totalUsers);
        response.put("buildings", buildings);

        return response;
    }

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
