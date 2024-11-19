package com.ssafy.fittapet.backend.application.service.diet;

import com.ssafy.fittapet.backend.domain.dto.diet.DietResponse;
import com.ssafy.fittapet.backend.domain.dto.health.HealthDietRequest;
import com.ssafy.fittapet.backend.domain.entity.User;

public interface DietService {

    Long createDietData(HealthDietRequest healthDietRequest, User user);

    DietResponse getDailyDietData(User user);
}
