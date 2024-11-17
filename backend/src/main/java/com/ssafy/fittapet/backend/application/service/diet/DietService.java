package com.ssafy.fittapet.backend.application.service.diet;

import com.ssafy.fittapet.backend.domain.dto.diet.DietRequestDto;
import com.ssafy.fittapet.backend.domain.dto.diet.DietResponseDto;
import com.ssafy.fittapet.backend.domain.entity.Diet;
import com.ssafy.fittapet.backend.domain.entity.Health;
import com.ssafy.fittapet.backend.domain.entity.User;

public interface DietService {

    // 식단 업데이트
    Diet createDietCurrentTime(DietRequestDto dietRequestDto, User loginUser);

    // 식단 관련 데이터 조회
    DietResponseDto getDietCurrentTime(User loginUser);
}
