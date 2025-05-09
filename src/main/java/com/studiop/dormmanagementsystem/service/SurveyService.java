package com.studiop.dormmanagementsystem.service;

import com.studiop.dormmanagementsystem.entity.Survey;
import com.studiop.dormmanagementsystem.entity.dto.SurveyDto;
import com.studiop.dormmanagementsystem.entity.enums.AppConfigKey;
import com.studiop.dormmanagementsystem.repository.SurveyRepository;
import jakarta.persistence.EntityNotFoundException;
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
            try {
                surveyTransactionService.updateSurvey();
                return "성공";
            } catch (Exception e) {
                log.error("작업 중 예외 발생: {}", e.getMessage(), e);
                return "실패: " + e.getMessage();
            }
        });
    }

    @Transactional
    public void deleteSurvey(Long id) {
        if (!surveyRepository.existsById(id)) {
            throw new EntityNotFoundException("해당 ID의 서약서를 찾을 수 없습니다: " + id);
        }
        surveyRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllSurveys() {
        surveyRepository.deleteAllInBatch();
        appConfigService.setConfigValue(AppConfigKey.LAST_FETCHED_TIME, LocalDateTime.MIN.toString());
    }
}