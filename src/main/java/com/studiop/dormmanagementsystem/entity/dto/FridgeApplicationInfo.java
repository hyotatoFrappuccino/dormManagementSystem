package com.studiop.dormmanagementsystem.entity.dto;

import com.studiop.dormmanagementsystem.entity.FridgeApplication;

public record FridgeApplicationInfo(
    Long id,
    Long roundId,
    String type
) {
    public static FridgeApplicationInfo fromEntity(FridgeApplication application) {
        return new FridgeApplicationInfo(
            application.getId(),
            application.getRound().getId(),
            application.getType().name()
        );
    }
}
