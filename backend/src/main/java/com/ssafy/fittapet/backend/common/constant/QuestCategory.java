package com.ssafy.fittapet.backend.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum QuestCategory {
    SLEEP("SLEEP"),
    WALK("WALK"),
    DIET("DIET");

    private final String name;
}
