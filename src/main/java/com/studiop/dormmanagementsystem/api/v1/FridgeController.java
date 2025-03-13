package com.studiop.dormmanagementsystem.api.v1;

import com.studiop.dormmanagementsystem.entity.Survey;
import com.studiop.dormmanagementsystem.service.PaymentService;
import com.studiop.dormmanagementsystem.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/fridge")
@RequiredArgsConstructor
public class FridgeController {

    private final PaymentService paymentService;
    private final SurveyService surveyService;

    @GetMapping("/member")
    public Map<String, Object> getMember(@RequestParam String studentId) {
        Map<String, Object> response = new HashMap<>();
        Survey survey = surveyService.getSurvey(studentId);
        String name = "-";
        String building = "-";
        String room = "-";
        if (survey != null) {
            name = survey.getName();
            building = survey.getBuilding().getName();
            room = survey.getRoomNumber();
        }
        response.put("name", name);
        response.put("isPaid", paymentService.isPaid(studentId));
        response.put("isSubmitted", surveyService.isSubmitted(studentId));
        response.put("building", building);
        response.put("roomNumber", room);

        return response;
    }

}
