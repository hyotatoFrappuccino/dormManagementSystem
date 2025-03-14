package com.studiop.dormmanagementsystem.api.v1;

import com.studiop.dormmanagementsystem.entity.Building;
import com.studiop.dormmanagementsystem.entity.Survey;
import com.studiop.dormmanagementsystem.service.PaymentService;
import com.studiop.dormmanagementsystem.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/fridge")
@RequiredArgsConstructor
public class FridgeController {

    private final PaymentService paymentService;
    private final SurveyService surveyService;

    @GetMapping("/member")
    public Map<String, Object> getMember(@RequestParam String studentId) {
        Optional<Survey> survey = surveyService.getSurvey(studentId);

        String name = survey.map(Survey::getName).orElse("-");
        String building = survey.flatMap(s -> Optional.ofNullable(s.getBuilding()).map(Building::getName)).orElse("-");
        String room = survey.map(Survey::getRoomNumber).orElse("-");
        boolean isPaid = paymentService.isPaid(studentId);
        boolean isAgreed = survey.map(Survey::isAgreed).orElse(false);

        return Map.of(
                "name", name,
                "isPaid", isPaid,
                "isAgreed", isAgreed,
                "building", building,
                "roomNumber", room
        );
    }
}
