package com.ssafy.fittapet.backend.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserTier {
    EASY("EASY"),
    NORMAL("NORMAL"),
    HARD("HARD");

    private final String value;
}
