package com.ssafy.fittapet.backend.domain.dto.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {

    private Long userId;
    private String userName;
    private String userUniqueName;
    private String role;
    private String userTier;
}
