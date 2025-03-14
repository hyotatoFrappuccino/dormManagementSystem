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

    private int totalUsers;

    public Building(String name, int totalSlots) {
        this.name = name;
        this.totalSlots = totalSlots;
        this.totalUsers = 0;
    }

    public int increaseUser() {
        this.totalUsers++;
        return this.totalUsers;
    }

    public int decreaseUser() {
        if (this.totalUsers > 0) {
            this.totalUsers--;
        }
        return this.totalUsers;
    }

    public int setTotalSlots(int totalSlots) {
        this.totalUsers = totalSlots;
        return this.totalUsers;
    }

    public void changeName(String newName) {
        this.name = newName;
    }
}
