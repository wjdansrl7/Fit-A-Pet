package com.ssafy.fitapet.backend.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PetType {
    BELUGA("벨루가"),
    LION("사자"),
    CHINCHILLA("친칠라"),
    WEASEL("족제비"),
    WHALE("범고래");

    private final String value;
}
