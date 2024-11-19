package com.ssafy.fittapet.backend.domain.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignupResponseDto {

    private Long userId;
    private String userUniqueName;
    private String role;
    private String accessToken;
    private String refreshToken;
    private boolean shouldShowModal;
    private String petType;
    private String petStatus;
}
