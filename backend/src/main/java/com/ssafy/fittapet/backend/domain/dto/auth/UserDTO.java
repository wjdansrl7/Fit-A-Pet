package com.ssafy.fittapet.backend.domain.dto.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDTO {

    private Long userId;
    private String userName;
    private String userNickname;
    private String role;
    private String userTier;
}
