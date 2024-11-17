package com.ssafy.fittapet.backend.domain.repository.diet;

import com.ssafy.fittapet.backend.domain.dto.diet.DietResponse;
import com.ssafy.fittapet.backend.domain.entity.User;

public interface DietCustomRepository {
    DietResponse findByUser(User user);
}
