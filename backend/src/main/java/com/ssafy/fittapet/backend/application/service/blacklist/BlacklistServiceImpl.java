package com.ssafy.fittapet.backend.application.service.blacklist;

import com.ssafy.fittapet.backend.domain.entity.Blacklist;
import com.ssafy.fittapet.backend.domain.repository.auth.BlacklistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BlacklistServiceImpl implements BlacklistService {

    private final BlacklistRepository blacklistRepository;

    @Override
    public boolean existsById(String refresh) {
        return blacklistRepository.existsById(refresh);
    }

    @Override
    public void saveBlacklist(Blacklist blacklist) {
        blacklistRepository.save(blacklist);
    }
}
