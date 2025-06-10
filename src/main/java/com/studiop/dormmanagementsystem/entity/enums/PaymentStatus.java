package com.studiop.dormmanagementsystem.entity.enums;

import java.util.Arrays;

public enum PaymentStatus {
    PAID, REFUNDED, NONE;

    public static PaymentStatus getPaymentStatus(String key) {
        return Arrays.stream(values())
                .filter(v -> v.name().equals(key))
                .findAny()
                .orElse(null);
    }
}