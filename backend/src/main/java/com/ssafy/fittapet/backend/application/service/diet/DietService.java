package com.ssafy.fittapet.backend.application.service.diet;

import com.ssafy.fittapet.backend.domain.dto.diet.DietResponse;
import com.ssafy.fittapet.backend.domain.dto.health.HealthDietRequest;
import com.ssafy.fittapet.backend.domain.entity.User;

public interface DietService {
    void createDietData(HealthDietRequest healthDietRequest, User user);

    DietResponse getDailyDietData(User user);

//    // 식단 업데이트
//    Diet createDietCurrentTime(DietRequestDto dietRequestDto, User loginUser);
//
//    // 식단 관련 데이터 조회
//    DietResponseDto getDietCurrentTime(User loginUser);
}
