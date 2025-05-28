package com.studiop.dormmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Round extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "round_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    private String password;

    @Builder.Default
    @OneToMany(mappedBy = "round", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FridgeApplication> fridgeApplications = new ArrayList<>();

    public void updateRound(String name, LocalDate startDate, LocalDate endDate, String password) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.password = password;
    }
}