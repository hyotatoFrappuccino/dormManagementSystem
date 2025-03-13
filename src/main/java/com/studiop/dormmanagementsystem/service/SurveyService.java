package com.studiop.dormmanagementsystem.service;

import com.studiop.dormmanagementsystem.entity.Building;
import com.studiop.dormmanagementsystem.entity.Survey;
import com.studiop.dormmanagementsystem.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@EnableAsync
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final BuildingService buildingService;
    private final GoogleSheetsService googleSheetsService;

    public List<Survey> getAllSurveys() {
        return surveyRepository.findAll();
    }

    public boolean isSubmitted(String studentId) {
        return surveyRepository.existsByStudentIdAndAgreedIsTrue(studentId);
    }

    public Survey getSurvey(String studentId) {
        return surveyRepository.findByStudentId(studentId).orElse(null);
    }

    @Async
    @Transactional
    public CompletableFuture<Void> updateSurveyFromGoogleSheets(LocalDateTime lastFetchedTime) {
        return CompletableFuture.runAsync(() -> {
            try {
                List<List<Object>> responses = googleSheetsService.getSurveyResponses();
                if (responses.isEmpty() || responses.size() == 1) {
                    return; // 빈 데이터 반환
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy. M. d a h:mm:ss")
                        .withLocale(java.util.Locale.KOREAN);
                for (int i = 1; i < responses.size(); i++) { // 첫 번째 행(헤더) 제외
                    List<Object> row = responses.get(i);

                    if (row.size() < 7) continue; // 데이터가 부족한 행 건너뛰기

                    LocalDateTime dateTime = LocalDateTime.parse(row.get(0).toString(), formatter);
                    if (dateTime.isBefore(lastFetchedTime)) {
                        continue;
                    }
                    String studentId = row.get(1).toString();
                    String name = row.get(2).toString();
                    String phoneNumber = row.get(3).toString();
                    String buildingName = row.get(4).toString();
                    String roomNumber = row.get(5).toString().replace("호", "");

                    boolean agreed = row.stream().noneMatch(cell -> cell.toString().contains("동의하지 않습니다"));

                    Building building = buildingService.getByName(buildingName);

                    Survey survey = new Survey(dateTime, studentId, name, phoneNumber, building, roomNumber, agreed);
                    surveyRepository.save(survey);
                }
            } catch (Exception e) {
                log.error("비동기 작업 중 예외 발생: {}", e.getMessage(), e);
                throw new RuntimeException(e.getMessage());
            }
        });
    }

    @Transactional
    public void deleteSurvey(Long id) {
        surveyRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllSurveys() {
        surveyRepository.deleteAll();
    }
}
