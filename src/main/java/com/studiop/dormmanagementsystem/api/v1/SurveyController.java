package com.studiop.dormmanagementsystem.api.v1;

import com.studiop.dormmanagementsystem.entity.Survey;
import com.studiop.dormmanagementsystem.service.AppConfigService;
import com.studiop.dormmanagementsystem.service.SurveyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/api/v1/survey")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;
    private final AppConfigService appConfigService;

    @PostMapping
    public CompletableFuture<ResponseEntity<String>> updateSurveyResponses() {
        String lastFetchedTime1 = appConfigService.getConfigValue("last_fetched_time", LocalDateTime.MIN.toString());
        LocalDateTime lastFetchedTime = LocalDateTime.parse(lastFetchedTime1, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        updateLastFetchedTime();

        return surveyService.updateSurveyFromGoogleSheets(lastFetchedTime)
                .thenApply(v -> ResponseEntity.ok("Survey updated successfully"))
                .exceptionally(ex -> ResponseEntity.internalServerError().body(ex.getCause().getMessage()));
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Survey>> get() {
        return ResponseEntity.ok(surveyService.getAllSurveys());
    }

    @DeleteMapping("/{id}")
    public void deleteSurvey(@PathVariable("id") Long id) {
        surveyService.deleteSurvey(id);
    }

    @DeleteMapping("/all")
    public void deleteAllSurveys() {
        surveyService.deleteAllSurveys();
        appConfigService.setConfigValue("last_fetched_time", LocalDateTime.MIN.toString());
    }

    public void updateLastFetchedTime() {
        appConfigService.setConfigValue("last_fetched_time", LocalDateTime.now().toString());
    }
}
