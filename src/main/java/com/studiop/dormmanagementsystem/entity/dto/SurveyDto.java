package com.studiop.dormmanagementsystem.entity.dto;

import com.studiop.dormmanagementsystem.entity.Survey;

import java.time.LocalDateTime;

public record SurveyDto(
        Long id,
        LocalDateTime dateTime,
        String studentId,
        String name,
        String phoneNumber,
        String buildingName,
        String roomNumber,
        boolean agreed
) {
    public static SurveyDto fromEntity(Survey survey) {
        return new SurveyDto(
                survey.getId(), survey.getDateTime(), survey.getStudentId(), survey.getName(), survey.getPhoneNumber(), survey.getBuilding().getName(), survey.getRoomNumber(), survey.isAgreed()
        );
    }
}