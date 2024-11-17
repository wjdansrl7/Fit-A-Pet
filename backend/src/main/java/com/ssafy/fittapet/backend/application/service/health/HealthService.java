package com.ssafy.fittapet.backend.application.service.health;

import com.ssafy.fittapet.backend.domain.entity.Health;
import com.ssafy.fittapet.backend.domain.entity.User;

public interface HealthService {

    // 수면 시간 업데이트
    Health createSleepTimeCurrentTime(Integer sleepTime, User loginUser);

    // 걸음수 업데이트
    Health createStepCntCurrentTime(Integer walkTime, User loginUser);

    // 헬스 데이터 관련 데이터 조회
    Health getHealthCurrentTime(User loginUser);

}
