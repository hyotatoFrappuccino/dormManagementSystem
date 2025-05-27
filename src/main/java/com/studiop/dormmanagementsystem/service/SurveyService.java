package com.studiop.dormmanagementsystem.service;

import com.studiop.dormmanagementsystem.entity.Survey;
import com.studiop.dormmanagementsystem.entity.dto.SurveyDto;
import com.studiop.dormmanagementsystem.entity.enums.AppConfigKey;
import com.studiop.dormmanagementsystem.exception.EntityException;
import com.studiop.dormmanagementsystem.exception.ErrorCode;
import com.studiop.dormmanagementsystem.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Service
@EnableAsync
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final SurveyTransactionService surveyTransactionService;
    private final AppConfigService appConfigService;

    public List<Survey> getSurveys(String studentId) {
        return surveyRepository.findByStudentId(studentId);
    }

    public List<SurveyDto> getAllSurveys() {
        List<Survey> surveys = surveyRepository.findAllWithBuilding();
        return surveys.stream()
                .map(SurveyDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Async
    public CompletableFuture<String> updateSurveyFromGoogleSheets() {
        return CompletableFuture.supplyAsync(() -> {
            surveyTransactionService.updateSurvey();
            return "성공";
        });
    }

    @Transactional
    public void deleteSurvey(Long id) {
        if (!surveyRepository.existsById(id)) {
            throw new EntityException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        surveyRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllSurveys() {
        surveyRepository.deleteAllInBatch();
        appConfigService.setConfigValue(AppConfigKey.LAST_FETCHED_TIME, LocalDateTime.MIN.toString());
    }
}