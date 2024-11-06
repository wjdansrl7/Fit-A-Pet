package com.ssafy.fittapet.backend.common.constant.entity_field;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    USER("USER"),
    ADMIN("ADMIN");

    private final String value;
}
