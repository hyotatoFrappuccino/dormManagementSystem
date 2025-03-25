package com.studiop.dormmanagementsystem.entity.dto;

import com.studiop.dormmanagementsystem.entity.FridgeApplication;
import com.studiop.dormmanagementsystem.entity.enums.FridgeType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class FridgeApplicationDto {
    private String roundName;
    private FridgeType type;

    public static FridgeApplicationDto fromEntity(FridgeApplication application) {
        return new FridgeApplicationDto(
                application.getRound().getName(),
                application.getType()
        );
    }
}
