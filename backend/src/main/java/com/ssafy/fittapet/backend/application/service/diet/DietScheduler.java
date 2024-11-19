package com.ssafy.fittapet.backend.application.service.diet;

import com.ssafy.fittapet.backend.domain.repository.diet.DietRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DietScheduler {

    private final DietRepository dietRepository;

    @Scheduled(cron = "30 0 0 * * *")
    @Transactional
    public void reportCurrentTime() {
        dietRepository.deleteAll();
    }
}
