package com.ssafy.fittapet.backend.application.service.health;

import com.ssafy.fittapet.backend.domain.entity.Health;
import com.ssafy.fittapet.backend.domain.entity.User;

public interface HealthService {
    Health saveSleepTimeCurrentTime(Integer sleepTime, User loginUser);
    Health saveStepCntCurrentTime(Integer walkTime, User loginUser);
    Health findHealthCurrentTime(User loginUser);

}
