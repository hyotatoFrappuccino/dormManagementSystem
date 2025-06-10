package com.studiop.dormmanagementsystem.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Role {
    PRESIDENT("ROLE_PRESIDENT", "회장"),
    VICE_PRESIDENT("ROLE_VICE_PRESIDENT", "부회장"),
    EXECUTIVE("ROLE_EXECUTIVE", "임원"),
    DEVELOPER("ROLE_DEVELOPER", "개발자");

    private final String key;
    private final String title;

    public static Role getRole(String key) {
        return Arrays.stream(values())
                .filter(v -> v.name().equals(key))
                .findAny()
                .orElse(null);
    }
}