package com.studiop.dormmanagementsystem.entity;

import com.studiop.dormmanagementsystem.entity.enums.FridgeType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Building extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "building_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Min(0)
    private int fridgeSlots; // 냉장 슬롯

    @Min(0)
    private int freezerSlots; // 냉동 슬롯

    @Min(0)
    private int integratedSlots; // 통합 슬롯

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FridgeType type;

    public void changeName(String newName) {
        this.name = newName;
    }

    public void changeFridgeSlots(int newFridgeSlots) {
        this.fridgeSlots = newFridgeSlots;
    }

    public void changeFreezerSlots(int newFreezerSlots) {
        this.freezerSlots = newFreezerSlots;
    }

    public void changeIntegratedSlots(int newIntegratedSlots) {
        this.integratedSlots = newIntegratedSlots;
    }

    public void changeType(FridgeType type) {
        this.type = type;
    }
}