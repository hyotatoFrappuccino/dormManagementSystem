package com.studiop.dormmanagementsystem.dto;

import com.studiop.dormmanagementsystem.entity.Building;
import lombok.Data;

@Data
public class BuildingDto {
    private String name;
    private int totalSlots;
    private int totalUsers;

//    @QueryProjection
    public BuildingDto(String name, int totalSlots, int totalUsers) {
        this.name = name;
        this.totalSlots = totalSlots;
        this.totalUsers = totalUsers;
    }

    public static BuildingDto from(Building building) {
        return new BuildingDto(building.getName(), building.getTotalSlots(), building.getTotalUsers());
    }
}
