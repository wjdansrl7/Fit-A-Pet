package com.ssafy.fittapet.backend.application.service.refresh;

import com.ssafy.fittapet.backend.domain.entity.RefreshToken;
import com.ssafy.fittapet.backend.domain.repository.auth.RefreshRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshServiceImpl implements RefreshService {

    private final RefreshRepository refreshRepository;

    @Override
    public Optional<RefreshToken> findRefreshTokenById(String userUniqueName) {
        return refreshRepository.findById(userUniqueName);
    }

    @Override
    public void deleteRefreshTokenById(String userUniqueName) {
        refreshRepository.deleteById(userUniqueName);
    }

    @Override
    public void saveRefreshToken(RefreshToken refreshToken) {
        refreshRepository.save(refreshToken);
    }
}
