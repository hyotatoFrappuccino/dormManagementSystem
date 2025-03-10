package com.studiop.dormmanagementsystem;

import com.studiop.dormmanagementsystem.entity.Building;
import com.studiop.dormmanagementsystem.service.BuildingService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class initDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.init1();
    }

    @Component
    @RequiredArgsConstructor
    static class InitService {

        private final BuildingService buildingService;

        @Transactional
        public void init1() {
            Building building1 = buildingService.addBuilding("새롬관(여)", 100);
            Building building2 = buildingService.addBuilding("새롬관(남)", 100);
            Building building3 = buildingService.addBuilding("이룸관(여)", 100);
            Building building4 = buildingService.addBuilding("이룸관(남)", 100);
            Building building5 = buildingService.addBuilding("예지원", 100);
            Building building6 = buildingService.addBuilding("난지원", 100);
            Building building7 = buildingService.addBuilding("국지원", 100);
            Building building8 = buildingService.addBuilding("퇴계관", 100);

            Random r = new Random();

            buildingService.setUserSlots(building1, r.nextInt(100));
            buildingService.setUserSlots(building2, r.nextInt(100));
            buildingService.setUserSlots(building3, r.nextInt(100));
            buildingService.setUserSlots(building4, r.nextInt(100));
            buildingService.setUserSlots(building5, r.nextInt(100));
            buildingService.setUserSlots(building6, r.nextInt(100));
            buildingService.setUserSlots(building7, r.nextInt(100));
            buildingService.setUserSlots(building8, r.nextInt(100));
        }
    }

}
