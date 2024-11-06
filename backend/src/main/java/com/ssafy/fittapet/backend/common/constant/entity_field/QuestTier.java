package com.ssafy.fittapet.backend.common.constant.entity_field;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum QuestTier {
    EASY("EASY"),
    NORMAL("NORMAL"),
    HARD("HARD");

    private final String value;
}