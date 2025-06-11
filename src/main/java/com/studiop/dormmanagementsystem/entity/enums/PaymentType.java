package com.studiop.dormmanagementsystem.entity.enums;

import java.util.Arrays;

public enum PaymentType {
    BANK_TRANSFER, ON_SITE;

    public static PaymentType getPaymentType(String key) {
        return Arrays.stream(values())
                .filter(v -> v.name().equals(key))
                .findAny()
                .orElse(null);
    }
}