package com.ssafy.fittapet.backend.domain.dto.auth;

import lombok.Builder;
import lombok.Data;

/**
 * packageName    : com.ssafy.fittapet.backend.domain.dto.auth
 * fileName       : SignupResponseDto
 * author         : moongi
 * date           : 11/16/24
 * description    :
 */
@Data
@Builder
public class SignupResponseDto {
    private String accessToken;
    private String refreshToken;
    private boolean shouldShowModal;
    private String petType;
    private String petStatus;

}
