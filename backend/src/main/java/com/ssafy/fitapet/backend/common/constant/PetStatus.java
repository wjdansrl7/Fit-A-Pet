package com.ssafy.fitapet.backend.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PetStatus {
    EGG("알"),
    SUBADULT("준성체"),
    ADULT("성체");

    private final String value;
}
