package com.studiop.dormmanagementsystem.service;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.studiop.dormmanagementsystem.entity.Building;
import com.studiop.dormmanagementsystem.entity.Survey;
import com.studiop.dormmanagementsystem.entity.enums.AppConfigKey;
import com.studiop.dormmanagementsystem.exception.GoogleSheetException;
import com.studiop.dormmanagementsystem.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.studiop.dormmanagementsystem.exception.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class SurveyTransactionService {

    private final GoogleSheetsService googleSheetsService;
    private final AppConfigService appConfigService;
    private final SurveyRepository surveyRepository;
    private final BuildingService buildingService;

    @Transactional
    public void updateSurvey() {
        List<List<Object>> responses;
        try {
            responses = googleSheetsService.getSurveyResponses();
        } catch (GoogleJsonResponseException e) {
            int statusCode = e.getStatusCode();
            switch (statusCode) {
                case 401:
                    throw new GoogleSheetException(GOOGLE_INVALID_API_KEY);
                case 403:
                    throw new GoogleSheetException(GOOGLE_NO_ACCESS);
                case 404:
                    throw new GoogleSheetException(GOOGLE_SHEET_ID_INVALID);
                case 429:
                    throw new GoogleSheetException(GOOGLE_EXCEED_API_REQUEST);
                case 500:
                    throw new GoogleSheetException(GOOGLE_500);
                case 503:
                    throw new GoogleSheetException(GOOGLE_503);
                default:
                    log.error("예상치 못한 Google API 오류 (HTTP {}): {}", statusCode, e.getDetails().getMessage(), e);
                    throw new GoogleSheetException(GOOGLE_ERROR);
            }
        } catch (GeneralSecurityException | IOException e) {
            log.error("Google Sheets API 오류: {}", e.getMessage(), e);
            throw new GoogleSheetException(GOOGLE_ERROR);
        }

        if (responses.isEmpty() || responses.size() == 1) {
            return; // 빈 데이터 반환
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy. M. d a h:mm:ss").withLocale(java.util.Locale.KOREAN);
        List<Survey> surveysToSave = new ArrayList<>();
        LocalDateTime lastFetchedTime = getAndUpdateLastFetchedTime();
        List<Building> buildingList = buildingService.getAllBuildingsEntity();

        for (int i = 1; i < responses.size(); i++) {
            List<Object> row = responses.get(i);
            LocalDateTime dateTime = LocalDateTime.parse(row.get(0).toString(), formatter);
            if (dateTime.isBefore(lastFetchedTime)) {
                continue;
            }

            String studentId = row.get(1).toString();
            String name = row.get(2).toString();
            String phoneNumber = formatPhoneNumber(row.get(3).toString());
            String buildingName = row.get(4).toString();
            String roomNumber = row.get(5).toString().replace("호", "");
            boolean agreed = row.stream().noneMatch(cell -> cell.toString().contains("동의하지 않습니다"));
            Building building = buildingList.stream().filter(b -> b.getName().equals(buildingName)).findFirst().orElse(null);

            Survey survey = Survey.builder()
                    .dateTime(dateTime)
                    .studentId(studentId)
                    .name(name)
                    .phoneNumber(phoneNumber)
                    .building(building)
                    .roomNumber(roomNumber)
                    .agreed(agreed)
                    .build();
            surveysToSave.add(survey);
        }

        if (!surveysToSave.isEmpty()) {
            surveyRepository.saveAll(surveysToSave);
        }
    }

    private LocalDateTime getAndUpdateLastFetchedTime() {
        // 마지막 업데이트 시간 이후에 추가된 설문만 가져오기
        String lastFetchedTimeConfig = appConfigService.getConfigValue(AppConfigKey.LAST_FETCHED_TIME, LocalDateTime.MIN.toString());
        LocalDateTime lastFetchedTime = LocalDateTime.parse(lastFetchedTimeConfig, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        appConfigService.setConfigValue(AppConfigKey.LAST_FETCHED_TIME, LocalDateTime.now().toString());
        return lastFetchedTime;
    }

    public String formatPhoneNumber(String rawPhoneNumber) {
        String digits = rawPhoneNumber.replaceAll("[^0-9]", "");

        if (digits.length() == 10) {
            return digits.replaceFirst("(\\d{2})(\\d{4})(\\d{4})", "0$1-$2-$3");
        } else if (digits.length() == 11) {
            return digits.replaceFirst("(\\d{3})(\\d{4})(\\d{4})", "$1-$2-$3");
        }

        return rawPhoneNumber;
    }
}