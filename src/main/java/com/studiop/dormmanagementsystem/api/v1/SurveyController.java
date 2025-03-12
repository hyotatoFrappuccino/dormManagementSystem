package com.studiop.dormmanagementsystem.api.v1;

import com.studiop.dormmanagementsystem.entity.Survey;
import com.studiop.dormmanagementsystem.service.BuildingService;
import com.studiop.dormmanagementsystem.service.GoogleSheetsService;
import com.studiop.dormmanagementsystem.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/survey")
@RequiredArgsConstructor
public class SurveyController {

    private final GoogleSheetsService googleSheetsService;
    private final BuildingService buildingService;
    private final SurveyService surveyService;

    @PostMapping
    public void getSurveyResponses() throws IOException, GeneralSecurityException {
        List<List<Object>> responses = googleSheetsService.getSurveyResponses();
        List<Survey> surveys = responses.stream()
                .skip(1) // 첫 번째 행(헤더) 건너뛰기
                .map(row -> SurveyParser.parseSurvey(row, buildingService.getAllBuildings()))
                .toList();
        for (Survey survey : surveys) {

        }
    }
}
