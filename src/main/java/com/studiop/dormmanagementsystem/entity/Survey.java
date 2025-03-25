package com.studiop.dormmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Survey {

    @Id
    @GeneratedValue
    @Column(name = "survey_id")
    private Long id;

    private LocalDateTime dateTime;

    private String studentId;

    private String name;

    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id")
    private Building building;

    private String roomNumber;

    private boolean agreed;

    public Survey(LocalDateTime dateTime, String studentId, String name, String phoneNumber, Building building, String roomNumber, boolean agreed) {
        this.dateTime = dateTime;
        this.studentId = studentId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.building = building;
        this.roomNumber = roomNumber;
        this.agreed = agreed;
    }
}
