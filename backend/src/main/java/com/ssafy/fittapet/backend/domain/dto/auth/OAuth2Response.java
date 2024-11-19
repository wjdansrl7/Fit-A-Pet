package com.ssafy.fittapet.backend.domain.dto.auth;

public interface OAuth2Response {

    String getProvider();

    String getProviderId();

    String getEmail();

    String getName();
}
