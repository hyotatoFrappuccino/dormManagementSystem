package com.studiop.dormmanagementsystem;

import com.studiop.dormmanagementsystem.entity.Building;
import com.studiop.dormmanagementsystem.entity.enums.PaymentType;
import com.studiop.dormmanagementsystem.service.BuildingService;
import com.studiop.dormmanagementsystem.service.PaymentService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
//        initService.mockBuilding();
//        initService.mockPayment();
    }

    @Component
    @RequiredArgsConstructor
    static class InitService {

        private final BuildingService buildingService;
        private final PaymentService paymentService;
        private static final ThreadLocalRandom r = ThreadLocalRandom.current();
        private static final int DEFAULT_BUILDING_CAPACITY = 100;
        private static final int PAYMENT_AMOUNT = 7000;

        @Transactional
        public void mockBuilding() {
            Building building1 = buildingService.addBuilding("새롬관(여)", DEFAULT_BUILDING_CAPACITY);
            Building building2 = buildingService.addBuilding("새롬관(남)", DEFAULT_BUILDING_CAPACITY);
            Building building3 = buildingService.addBuilding("이룸관(여)", DEFAULT_BUILDING_CAPACITY);
            Building building4 = buildingService.addBuilding("이룸관(남)", DEFAULT_BUILDING_CAPACITY);
            Building building5 = buildingService.addBuilding("예지원", DEFAULT_BUILDING_CAPACITY);
            Building building6 = buildingService.addBuilding("난지원", DEFAULT_BUILDING_CAPACITY);
            Building building7 = buildingService.addBuilding("국지원", DEFAULT_BUILDING_CAPACITY);
            Building building8 = buildingService.addBuilding("퇴계관", DEFAULT_BUILDING_CAPACITY);

            buildingService.setUserSlots(building1, r.nextInt(DEFAULT_BUILDING_CAPACITY));
            buildingService.setUserSlots(building2, r.nextInt(DEFAULT_BUILDING_CAPACITY));
            buildingService.setUserSlots(building3, r.nextInt(DEFAULT_BUILDING_CAPACITY));
            buildingService.setUserSlots(building4, r.nextInt(DEFAULT_BUILDING_CAPACITY));
            buildingService.setUserSlots(building5, r.nextInt(DEFAULT_BUILDING_CAPACITY));
            buildingService.setUserSlots(building6, r.nextInt(DEFAULT_BUILDING_CAPACITY));
            buildingService.setUserSlots(building7, r.nextInt(DEFAULT_BUILDING_CAPACITY));
            buildingService.setUserSlots(building8, r.nextInt(DEFAULT_BUILDING_CAPACITY));
        }

        @Transactional
        public void mockPayment() {
            paymentService.addPayment("202112648", PAYMENT_AMOUNT, LocalDate.now(), PaymentType.BANK_TRANSFER);
            paymentService.addPayment("202112648", PAYMENT_AMOUNT, LocalDate.now().minusDays(1), PaymentType.ON_SITE);

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
                paymentService.addPayment("202" + year + sid, PAYMENT_AMOUNT, LocalDate.now().minusDays(day), paymentType);
            }
        }
    }

}
