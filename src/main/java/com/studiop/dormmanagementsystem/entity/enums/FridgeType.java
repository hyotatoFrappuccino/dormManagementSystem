package com.studiop.dormmanagementsystem.entity.enums;

import java.util.Arrays;

public enum FridgeType {
    REFRIGERATOR, // 냉장
    FREEZER,      // 냉동
    COMBINED,     // 통합 (냉장+냉동)
    ALL;           // 냉장, 냉동 각 모두 사용(Building)

    public static FridgeType getFridgeType(String key) {
        return Arrays.stream(values())
                .filter(v -> v.name().equals(key))
                .findAny()
                .orElse(null);
    }
}