package com.studiop.dormmanagementsystem.service;

import com.studiop.dormmanagementsystem.entity.Building;
import com.studiop.dormmanagementsystem.entity.Survey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FridgeService {
    private final PaymentService paymentService;
    private final SurveyService surveyService;

    public Map<String, Object> getMemberInfo(String studentId) {
        Optional<Survey> survey = surveyService.getSurvey(studentId);

        return Map.of(
                "name", survey.map(Survey::getName).orElse("-"),
                "isPaid", paymentService.isPaid(studentId),
                "isAgreed", survey.map(Survey::isAgreed).orElse(false),
                "building", survey.flatMap(s -> Optional.ofNullable(s.getBuilding()).map(Building::getName)).orElse("-"),
                "roomNumber", survey.map(Survey::getRoomNumber).orElse("-")
        );
    }
}
