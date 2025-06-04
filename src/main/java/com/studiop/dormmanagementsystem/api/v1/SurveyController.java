package com.studiop.dormmanagementsystem.api.v1;

import com.studiop.dormmanagementsystem.entity.dto.SurveyDto;
import com.studiop.dormmanagementsystem.service.SurveyService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/surveys")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    @Operation(summary = "서약서 업데이트")
    @PostMapping
    public ResponseEntity<String> updateSurveyResponses() throws ExecutionException, InterruptedException {
        CompletableFuture<String> futureResult = surveyService.updateSurveyFromGoogleSheets();
        String result = futureResult.get();
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "전체 서약서 목록 반환")
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<SurveyDto>> getAllSurveys() {
        return ResponseEntity.ok(surveyService.getAllSurveys());
    }

    @Operation(summary = "서약서 삭제")
    @DeleteMapping("/{id}")
    public void deleteSurvey(@PathVariable("id") Long id) {
        surveyService.deleteSurvey(id);
    }

    @Operation(summary = "전체 서약서 삭제")
    @PreAuthorize("hasRole('PRESIDENT')")
    @DeleteMapping
    public void deleteAllSurveys() {
        surveyService.deleteAllSurveys();
    }
}