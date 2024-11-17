package com.ssafy.fittapet.backend.application.controller;


import com.ssafy.fittapet.backend.application.service.auth.AuthService;
import com.ssafy.fittapet.backend.application.service.health.HealthService;
import com.ssafy.fittapet.backend.domain.dto.health.HealthSleepDto;
import com.ssafy.fittapet.backend.domain.dto.health.HealthStepDto;
import com.ssafy.fittapet.backend.domain.entity.Health;
import com.ssafy.fittapet.backend.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/healths")
public class HealthController {
    private final HealthService healthService;
    private final AuthService authService;

    @PostMapping("/walk")
    public ResponseEntity<?> createStepCnt(@RequestBody HealthStepDto healthStepDto) {
        User loginUser = authService.getLoginUser(1L);

        Health stepCntCurrentTime = healthService.createStepCntCurrentTime(
                healthStepDto.getStepCnt(), loginUser);

        return new ResponseEntity<>(stepCntCurrentTime.getId(), HttpStatus.CREATED);
    }

    @PostMapping("/sleep")
    public ResponseEntity<?> createSleepTime(@RequestBody HealthSleepDto healthSleepDto) {
        User loginUser = authService.getLoginUser(1L);
        Health sleepTimeCurrentTime = healthService.createSleepTimeCurrentTime(
                healthSleepDto.getSleepTime(), loginUser);

        return new ResponseEntity<>(sleepTimeCurrentTime.getId(), HttpStatus.CREATED);
    }

    @GetMapping("/walk")
    public ResponseEntity<?> getStepCntCurrentTime() {
        User loginUser = authService.getLoginUser(1L);
        Health healthCurrentTime = healthService.getHealthCurrentTime(loginUser);

        return new ResponseEntity<>(healthCurrentTime.getStepCnt(), HttpStatus.OK);

    }

    @GetMapping("/sleep")
    public ResponseEntity<?> getSleepTimeCurrentTime() {
        User loginUser = authService.getLoginUser(1L);
        Health healthCurrentTime = healthService.getHealthCurrentTime(loginUser);

        return new ResponseEntity<>(healthCurrentTime.getSleepTime(), HttpStatus.OK);
    }


}
