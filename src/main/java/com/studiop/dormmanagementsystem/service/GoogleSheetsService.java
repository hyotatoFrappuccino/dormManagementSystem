package com.studiop.dormmanagementsystem.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoogleSheetsService {

    private final AppConfigService appConfigService;

    private static final String APPLICATION_NAME = "Google Sheets API Spring Boot";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String RANGE = "A:Z"; // 가져올 데이터 범위

    private Sheets getSheetsService() throws IOException, GeneralSecurityException {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        // 서비스 계정 JSON 키 로드
        FileInputStream serviceAccountStream = new FileInputStream("src/main/resources/credentials.json");

        Credential credential = GoogleCredential.fromStream(serviceAccountStream)
                .createScoped(List.of("https://www.googleapis.com/auth/spreadsheets.readonly"));

        return new Sheets.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public List<List<Object>> getSurveyResponses() throws IOException, GeneralSecurityException {
        String SPREADSHEET_ID = appConfigService.getConfigValue("googleSheetId", "");
        if (SPREADSHEET_ID.isEmpty()) {
            throw new IllegalStateException("설정에서 서약서 구글 시트 ID를 설정해주세요.");
        }

        Sheets sheetsService = getSheetsService();
        ValueRange response = sheetsService.spreadsheets().values()
                .get(SPREADSHEET_ID, RANGE)
                .execute();

        return response.getValues();
    }
}
