package com.studiop.dormmanagementsystem.entity;

import com.studiop.dormmanagementsystem.entity.enums.FridgeType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FridgeApplication {

    @Id
    @GeneratedValue()
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Round round;

    @Enumerated(EnumType.STRING)
    private FridgeType type; // 냉장/냉동/통합

    private LocalDateTime appliedAt; // 신청 시간

    public FridgeApplication(Round round, FridgeType type) {
        this.round = round;
        this.type = type;
        this.appliedAt = LocalDateTime.now();
    }
}
