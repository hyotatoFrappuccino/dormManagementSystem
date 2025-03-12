package com.studiop.dormmanagementsystem.service;

import com.studiop.dormmanagementsystem.entity.Survey;
import com.studiop.dormmanagementsystem.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;

    public void getSurveyFromGoogleSheets(String googleSheetId) {

    }

    @Transactional
    public void addSurvey(Survey survey) {
        surveyRepository.save(survey);
    }
}
