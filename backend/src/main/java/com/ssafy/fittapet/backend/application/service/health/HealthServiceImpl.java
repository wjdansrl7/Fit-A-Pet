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
    public Health createSleepTimeCurrentTime(Integer sleepTime, User loginUser) {
        // 현재 날짜에 해당하는 health 정보 찾기
        Health currentHealth = healthRepository.findByUserAndCreatedAt(loginUser,
                LocalDateTime.now());

        // 생성된 활동 데이터가 없다면 생성
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
    public Health createStepCntCurrentTime(Integer stepCnt, User loginUser) {
        // 현재 날짜에 해당하는 health 정보 찾기
        Health currentHealth = healthRepository.findByUserAndCreatedAt(loginUser,
                LocalDateTime.now());

        // 생성된 활동 데이터가 없다면 생성
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
    public Health getHealthCurrentTime(User loginUser) {
        return healthRepository.findByUserAndCreatedAt(loginUser, LocalDateTime.now());
    }
}
