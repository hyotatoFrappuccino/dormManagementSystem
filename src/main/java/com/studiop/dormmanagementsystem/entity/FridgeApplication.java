package com.studiop.dormmanagementsystem.entity;

import com.studiop.dormmanagementsystem.entity.enums.FridgeType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class FridgeApplication extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "fridgeApplication_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id")
    private Building building;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "round_id")
    private Round round;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FridgeType type; // 냉장/냉동/통합

    @Column(nullable = false)
    private LocalDateTime appliedAt; // 신청 시간
}