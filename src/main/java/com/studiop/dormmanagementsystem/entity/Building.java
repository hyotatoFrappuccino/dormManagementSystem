package com.studiop.dormmanagementsystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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

    private int totalSlots;

    public Building(String name, int totalSlots) {
        this.name = name;
        this.totalSlots = totalSlots;
    }

    public void changeName(String newName) {
        this.name = newName;
    }

    public void changeTotalSlots(int newTotalSlots) {
        this.totalSlots = newTotalSlots;
    }
}
