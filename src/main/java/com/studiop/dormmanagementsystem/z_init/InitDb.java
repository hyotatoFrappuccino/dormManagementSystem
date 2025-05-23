//package com.studiop.dormmanagementsystem.z_init;
//
//import com.studiop.dormmanagementsystem.entity.dto.PaymentRequest;
//import com.studiop.dormmanagementsystem.entity.dto.RoundRequest;
//import com.studiop.dormmanagementsystem.entity.enums.FridgeType;
//import com.studiop.dormmanagementsystem.entity.enums.PaymentType;
//import com.studiop.dormmanagementsystem.service.AppConfigService;
//import com.studiop.dormmanagementsystem.service.BuildingService;
//import com.studiop.dormmanagementsystem.service.PaymentService;
//import com.studiop.dormmanagementsystem.service.RoundService;
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.util.concurrent.ThreadLocalRandom;
//
//@Component
//@RequiredArgsConstructor
//public class InitDb {
//
//    private final InitService initService;
//
//    @PostConstruct
//    public void init() {
//        initService.mockBuilding();
//        initService.mockPayment();
//        initService.setupGoogleSheetId();
//        initService.generateMockRounds();
//    }
//
//    @Component
//    @RequiredArgsConstructor
//    static class InitService {
//
//        private final BuildingService buildingService;
//        private final AppConfigService appConfigService;
//        private static final ThreadLocalRandom r = ThreadLocalRandom.current();
//        private static final int PAYMENT_AMOUNT = 7000;
//        private final RoundService roundService;
//        private final PaymentService paymentService;
//
//        @Transactional
//        public void mockBuilding() {
//            buildingService.addBuilding("새롬관(여)", 32, 25, 0, FridgeType.ALL);
//            buildingService.addBuilding("새롬관(남)", 30, 20, 0, FridgeType.ALL);
//            buildingService.addBuilding("이룸관(여)", 25, 20, 0, FridgeType.ALL);
//            buildingService.addBuilding("이룸관(남)", 20, 15, 0, FridgeType.ALL);
//            buildingService.addBuilding("예지원", 20, 15, 0, FridgeType.ALL);
//            buildingService.addBuilding("난지원", 30, 20, 0, FridgeType.ALL);
//            buildingService.addBuilding("국지원", 30, 20, 0, FridgeType.ALL);
//            buildingService.addBuilding("퇴계관", 20, 15, 0, FridgeType.ALL);
//        }
//
//        @Transactional
//        public void mockPayment() {
//            paymentService.addPayment(new PaymentRequest("202112648", PAYMENT_AMOUNT, LocalDate.now(), PaymentType.BANK_TRANSFER));
//            paymentService.addPayment(new PaymentRequest("202112648", PAYMENT_AMOUNT, LocalDate.now().minusDays(1), PaymentType.ON_SITE));
//
//            for (int i = 0; i < 500; i++) {
//                int year = r.nextInt(5);
//                int sid = r.nextInt(90000) + 10000;
//
//                int day = r.nextInt(60);
//                int type = r.nextInt(2);
//
//                PaymentType paymentType;
//                if (type == 1) {
//                    paymentType = PaymentType.BANK_TRANSFER;
//                } else {
//                    paymentType = PaymentType.ON_SITE;
//                }
//                paymentService.addPayment(new PaymentRequest("202" + year + sid, PAYMENT_AMOUNT, LocalDate.now().minusDays(day), paymentType));
//            }
//        }
//
//        @Transactional
//        public void setupGoogleSheetId() {
//            appConfigService.setConfigValue("googleSheetId", "1-I1jGTrlwqSwyBB-NpGkMeltbe4MjxyM27kDVztnA2A");
//        }
//
//        @Transactional
//        public void generateMockRounds() {
//            // 1학기 회차 (2주 단위로 8개)
//            LocalDate springStart = LocalDate.of(2025, 3, 4);
//            for (int i = 0; i < 8; i++) {
//                LocalDate startDate = springStart.plusDays(i * 14);
//                LocalDate endDate = startDate.plusDays(13);
//                roundService.addRound(new RoundRequest((i + 1) + "회차", startDate, endDate, "0000"));
//            }
//
//            // 여름방학
//            LocalDate summerStart = LocalDate.of(2025, 6, 24);
//            LocalDate fallStart = LocalDate.of(2025, 9, 1);
//            roundService.addRound(new RoundRequest("여름학기", summerStart, fallStart.minusDays(1), "0000"));
//
//            // 2학기 회차 (2주 단위로 8개)
//            for (int i = 0; i < 8; i++) {
//                LocalDate startDate = fallStart.plusDays(i * 14);
//                LocalDate endDate = startDate.plusDays(13);
//                roundService.addRound(new RoundRequest((i + 1) + "회차", startDate, endDate, "0000"));
//            }
//
//            // 겨울방학
//            roundService.addRound(new RoundRequest("겨울학기", LocalDate.of(2025, 12, 15), LocalDate.of(2026, 2, 28), "0000"));
//        }
//    }
//}
