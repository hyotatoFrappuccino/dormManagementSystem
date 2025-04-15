package com.studiop.dormmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Survey extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "survey_id")
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private String studentId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id")
    private Building building;

    @Column(nullable = false)
    private String roomNumber;

    @Column(nullable = false)
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