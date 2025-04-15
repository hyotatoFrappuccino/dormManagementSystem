package com.studiop.dormmanagementsystem.entity.dto;

import com.studiop.dormmanagementsystem.entity.Survey;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SurveyDto {

    private Long id;
    private LocalDateTime dateTime;
    private String studentId;
    private String name;
    private String phoneNumber;
    private BuildingDto building;
    private String roomNumber;
    private boolean agreed;

    public static SurveyDto fromEntity(Survey survey) {
        return new SurveyDto(
                survey.getId(), survey.getDateTime(), survey.getStudentId(), survey.getName(), survey.getPhoneNumber(), BuildingDto.fromEntity(survey.getBuilding()), survey.getRoomNumber(), survey.isAgreed()
        );
    }
}