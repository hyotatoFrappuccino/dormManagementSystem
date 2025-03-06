package com.studiop.dormmanagementsystem;

import com.studiop.dormmanagementsystem.service.BuildingService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
            buildingService.addBuilding("새롬관(여)", 100);
            buildingService.addBuilding("새롬관(남)", 100);
            buildingService.addBuilding("이룸관(여)", 100);
            buildingService.addBuilding("이룸관(남)", 100);
            buildingService.addBuilding("예지원", 100);
            buildingService.addBuilding("난지원", 100);
            buildingService.addBuilding("국지원", 100);
            buildingService.addBuilding("퇴계관", 100);

            buildingService.setUserSlots(buildingService.getBuildingById(1L).get(), 105);

            for (int i = 0; i < 10; i++) {
                buildingService.increaseUser(buildingService.getBuildingById(2L).get());
            }
            for (int i = 0; i < 100; i++) {
                buildingService.increaseUser(buildingService.getBuildingById(3L).get());
            }
            for (int i = 0; i < 10; i++) {
                buildingService.increaseUser(buildingService.getBuildingById(4L).get());
            }
            for (int i = 0; i < 30; i++) {
                buildingService.increaseUser(buildingService.getBuildingById(5L).get());
            }
            for (int i = 0; i < 40; i++) {
                buildingService.increaseUser(buildingService.getBuildingById(6L).get());
            }
            for (int i = 0; i < 70; i++) {
                buildingService.increaseUser(buildingService.getBuildingById(7L).get());
            }
            for (int i = 0; i < 100; i++) {
                buildingService.increaseUser(buildingService.getBuildingById(8L).get());
            }
        }
    }

}
