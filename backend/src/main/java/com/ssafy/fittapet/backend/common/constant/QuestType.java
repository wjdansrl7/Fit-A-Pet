package com.ssafy.fittapet.backend.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum QuestType {
    GROUP("GROUP"),
    PERSONAL("PERSONAL");

    private final String value;
}
