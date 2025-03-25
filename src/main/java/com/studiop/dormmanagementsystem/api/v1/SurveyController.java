package com.studiop.dormmanagementsystem.api.v1;

import com.studiop.dormmanagementsystem.entity.Survey;
import com.studiop.dormmanagementsystem.service.SurveyService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("/api/v1/surveys")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    @Operation(summary = "서약서 업데이트")
    @PostMapping
    public ResponseEntity<String> updateSurveyResponses() {
        log.info("Controller - start updateSurveyResponses");
        CompletableFuture<String> futureResult = surveyService.updateSurveyFromGoogleSheets();

        try {
            String result = futureResult.get(); // CompletableFuture 결과 기다리기
            if (result.startsWith("실패")) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body (result);
            }
            return ResponseEntity.ok(result);
        } catch (InterruptedException | ExecutionException e) {
            log.error("비동기 작업 대기 중 예외 발생: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("비동기 작업 실패");
        }
    }

    @Operation(summary = "전체 서약서 목록 반환")
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Survey>> getAllSurveys() {
        return ResponseEntity.ok(surveyService.getAllSurveys());
    }

    @Operation(summary = "서약서 삭제")
    @DeleteMapping("/{id}")
    public void deleteSurvey(@PathVariable("id") Long id) {
        surveyService.deleteSurvey(id);
    }

    @Operation(summary = "전체 서약서 삭제")
    @DeleteMapping
    public void deleteAllSurveys() {
        surveyService.deleteAllSurveys();
    }
}
