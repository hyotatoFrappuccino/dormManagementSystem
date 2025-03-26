package com.studiop.dormmanagementsystem.entity;

import com.studiop.dormmanagementsystem.entity.enums.FridgeType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Building {

    @Id
    @GeneratedValue
    @Column(name ="building_id")
    private Long id;

    private String name;

    private int fridgeSlots; // 냉장 슬롯
    private int freezerSlots; // 냉동 슬롯
    private int integratedSlots; // 통합 슬롯

    @Enumerated(EnumType.STRING)
    private FridgeType type;

    public Building(String name, int fridgeSlots, int freezerSlots, int integratedSlots, FridgeType type) {
        this.name = name;
        this.fridgeSlots = fridgeSlots;
        this.freezerSlots = freezerSlots;
        this.integratedSlots = integratedSlots;
        this.type = type;
    }

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
