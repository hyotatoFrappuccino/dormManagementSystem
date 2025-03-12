package com.studiop.dormmanagementsystem.api.v1;

import com.studiop.dormmanagementsystem.entity.Building;
import com.studiop.dormmanagementsystem.entity.Survey;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SurveyParser {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy. M. d a h:mm:ss");

    public static Survey parseSurvey(List<Object> row, List<Building> buildings) {
        try {
            // 필수 필드 추출
            LocalDateTime dateTime = parseDateTime(row.get(0).toString());
            String studentId = row.get(1).toString();
            String name = row.get(2).toString();
            String phoneNumber = parsePhoneNumber(row.get(3).toString());
            Building building = findBuilding(row.get(4).toString(), buildings);
            String roomNumber = row.get(5).toString();

            // 동의 여부 확인 (7번째 열부터 '동의하지 않습니다' 포함 여부 체크)
            boolean agreed = true;
            for (int i = 6; i < row.size(); i++) {
                if (row.get(i).toString().contains("동의하지 않습니다")) {
                    agreed = false;
                    break;
                }
            }

            return new Survey(dateTime, studentId, name, phoneNumber, building, roomNumber, agreed);
        } catch (Exception e) {
            throw new RuntimeException("설문 데이터 파싱 오류: " + e.getMessage(), e);
        }
    }

    private static LocalDateTime parseDateTime(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr.replace("오전", "AM").replace("오후", "PM"), FORMATTER);
    }

    private static String parsePhoneNumber(String phone) {
        return phone.replaceAll("[^0-9]", "");
    }

    private static Building findBuilding(String buildingName, List<Building> buildings) {
        return buildings.stream()
                .filter(b -> b.getName().equals(buildingName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 건물을 찾을 수 없습니다: " + buildingName));
    }
}
