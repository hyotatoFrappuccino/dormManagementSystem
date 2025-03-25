package com.studiop.dormmanagementsystem.api.v1;

import com.studiop.dormmanagementsystem.entity.Building;
import com.studiop.dormmanagementsystem.service.BuildingService;
import com.studiop.dormmanagementsystem.service.PaymentService;
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

    /**
     * 대시보드
     * @return 전체 납부자 수, 전체 이용자 수, 건물별 이용자 수
     */
    @GetMapping("/dashboard")
    public Map<String, Object> dashboard() {

        List<Building> buildings = buildingService.getAllBuildings();
        long totalPayers = paymentService.getNumOfTotalPayers();

        return Map.of(
                "totalPayers", totalPayers,
                "buildings", buildings
        );
    }
}
