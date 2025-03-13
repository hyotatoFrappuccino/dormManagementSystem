package com.studiop.dormmanagementsystem;

import com.studiop.dormmanagementsystem.entity.Building;
import com.studiop.dormmanagementsystem.entity.PaymentType;
import com.studiop.dormmanagementsystem.service.BuildingService;
import com.studiop.dormmanagementsystem.service.PaymentService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
        private final PaymentService paymentService;

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

            paymentService.addPayment("202112648", 7000, LocalDate.now(), PaymentType.BANK_TRANSFER);
            paymentService.addPayment("202112648", 7000, LocalDate.now().minusDays(1), PaymentType.ON_SITE);

            for (int i = 0; i < 500; i++) {
                int year = r.nextInt(5);
                int sid = r.nextInt(90000) + 10000;

                int day = r.nextInt(60);
                int type = r.nextInt(2);

                PaymentType paymentType;
                if (type == 1) {
                    paymentType = PaymentType.BANK_TRANSFER;
                } else {
                    paymentType = PaymentType.ON_SITE;
                }
                paymentService.addPayment("202" + year + sid, 7000, LocalDate.now().minusDays(day), paymentType);
            }

        }
    }

}
