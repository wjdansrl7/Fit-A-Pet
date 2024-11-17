package com.ssafy.fittapet.backend.application.service.refresh;

import com.ssafy.fittapet.backend.domain.entity.RefreshToken;

import java.util.Optional;

public interface RefreshService {

    Optional<RefreshToken> findRefreshTokenById(String userUniqueName);

    void deleteRefreshTokenById(String userUniqueName);

    void saveRefreshToken(RefreshToken refreshToken);
}
