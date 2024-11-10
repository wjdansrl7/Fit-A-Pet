package com.ssafy.fittapet.backend.domain.dto.auth;

import lombok.Builder;

@Builder
public class TokenDTO {

    private String accessToken;
    private String refreshToken;
}
