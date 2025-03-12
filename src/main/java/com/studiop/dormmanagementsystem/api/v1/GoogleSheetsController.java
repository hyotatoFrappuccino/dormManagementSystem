package com.studiop.dormmanagementsystem.api.v1;

import com.studiop.dormmanagementsystem.service.GoogleSheetsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class GoogleSheetsController {

    private final GoogleSheetsService googleSheetsService;

    @GetMapping("/sheetResponses")
    public List<List<Object>> getSurveyResponses() throws IOException, GeneralSecurityException {
        return googleSheetsService.getSurveyResponses();
    }
}
