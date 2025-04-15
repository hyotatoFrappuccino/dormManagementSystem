package com.studiop.dormmanagementsystem.api.v1;

import com.studiop.dormmanagementsystem.entity.dto.BuildingDto;
import com.studiop.dormmanagementsystem.service.BuildingService;
import com.studiop.dormmanagementsystem.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DashboardController {

    private final BuildingService buildingService;
    private final PaymentService paymentService;

    @Operation(summary = "대시보드 조회")
    @GetMapping("/dashboard")
    public Map<String, Object> dashboard() {

        List<BuildingDto> buildings = buildingService.getAllBuildings();
        long totalPayers = paymentService.getNumOfTotalPayers();

        return Map.of(
                "totalPayers", totalPayers,
                "buildings", buildings
        );
    }
}