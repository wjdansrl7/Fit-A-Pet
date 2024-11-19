package com.ssafy.fittapet.backend.application.service.diet;

import com.ssafy.fittapet.backend.domain.dto.diet.DietResponse;
import com.ssafy.fittapet.backend.domain.dto.health.HealthDietRequest;
import com.ssafy.fittapet.backend.domain.entity.Diet;
import com.ssafy.fittapet.backend.domain.entity.User;
import com.ssafy.fittapet.backend.domain.repository.diet.DietRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class DietServiceImpl implements DietService{

    private final DietRepository dietRepository;

    @Override
    public Long createDietData(HealthDietRequest healthDietRequest, User user) {

        Diet diet = Diet.fromRequest(healthDietRequest, user);

        dietRepository.save(diet);
        return diet.getId();
    }

    @Override
    public DietResponse getDailyDietData(User user) {
        return dietRepository.findByUser(user);
    }
}
