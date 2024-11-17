package com.ssafy.fittapet.backend.application.service.health;

import com.ssafy.fittapet.backend.domain.entity.Health;
import com.ssafy.fittapet.backend.domain.entity.User;
import com.ssafy.fittapet.backend.domain.repository.health.HealthRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class HealthServiceImpl implements HealthService {
    private final HealthRepository healthRepository;
    @Override
    public Health saveSleepTimeCurrentTime(Integer sleepTime, User loginUser) {
        Health currentHealth = healthRepository.findByUserAndCreatedAt(loginUser,
                LocalDateTime.now());

        if (currentHealth == null) {
            Health health = Health.builder().
                    user(loginUser)
                    .sleepTime(sleepTime)
                    .build();
            return healthRepository.save(health);
        }
        currentHealth.updateSleepTime(sleepTime);
        return currentHealth;
    }

    @Override
    public Health saveStepCntCurrentTime(Integer stepCnt, User loginUser) {
        Health currentHealth = healthRepository.findByUserAndCreatedAt(loginUser,
                LocalDateTime.now());

        if (currentHealth == null) {
            Health health = Health.builder().
                    user(loginUser)
                    .stepCnt(stepCnt)
                    .build();
            return healthRepository.save(health);
        }
        currentHealth.updateStepCnt(stepCnt);
        return currentHealth;
    }
    @Override
    public Health findHealthCurrentTime(User loginUser) {
        return healthRepository.findByUserAndCreatedAt(loginUser, LocalDateTime.now());
    }
}
