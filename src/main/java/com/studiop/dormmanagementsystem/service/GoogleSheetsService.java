package com.studiop.dormmanagementsystem.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.api.client.http.HttpRequestInitializer;
import com.studiop.dormmanagementsystem.entity.enums.AppConfigKey;
import com.studiop.dormmanagementsystem.exception.GoogleSheetException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import static com.studiop.dormmanagementsystem.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class GoogleSheetsService {

    private final AppConfigService appConfigService;

    private static final String APPLICATION_NAME = "Google Sheets API Spring Boot";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String RANGE = "A:Z"; // 가져올 데이터 범위

    private Sheets getSheetsService() throws IOException, GeneralSecurityException {
        var httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        // 서비스 계정 JSON 키 로드
        FileInputStream serviceAccountStream = new FileInputStream("src/main/resources/credentials.json");

        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccountStream)
                .createScoped(List.of("https://www.googleapis.com/auth/spreadsheets.readonly"));

        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);

        return new Sheets.Builder(httpTransport, JSON_FACTORY, requestInitializer)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public List<List<Object>> getSurveyResponses() throws IOException, GeneralSecurityException {
        String SPREADSHEET_ID = appConfigService.getConfigValue(AppConfigKey.GOOGLE_SHEET_ID, "");
        if (SPREADSHEET_ID.isEmpty()) {
            throw new GoogleSheetException(INVALID_REQUEST, "설정에서 서약서 구글 시트 ID를 설정해주세요.");
        }

        Sheets sheetsService = getSheetsService();
        ValueRange response = sheetsService.spreadsheets().values()
                .get(SPREADSHEET_ID, RANGE)
                .execute();

        return response.getValues();
    }
}