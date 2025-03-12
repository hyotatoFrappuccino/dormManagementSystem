package com.studiop.dormmanagementsystem.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Service
public class GoogleSheetsService {

    private static final String APPLICATION_NAME = "Google Sheets API Spring Boot";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String SPREADSHEET_ID = "1bFGORsfUHgQqpKqvW7Xhq24ibw8en3DSTlK_AQgFH7U"; // 구글 시트 ID
    private static final String RANGE = "A:Z"; // 가져올 데이터 범위
//    knu-dorm-management-system@knu-dorm-management-system.iam.gserviceaccount.com
//    1분당 읽기요청 : 300

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
        Sheets sheetsService = getSheetsService();
        ValueRange response = sheetsService.spreadsheets().values()
                .get(SPREADSHEET_ID, RANGE)
                .execute();

        return response.getValues(); // 2D 리스트 형태로 응답 반환
    }
}
