package com.ssafy.fittapet.backend.application.controller;


import com.ssafy.fittapet.backend.application.service.auth.AuthService;
import com.ssafy.fittapet.backend.application.service.health.HealthService;
import com.ssafy.fittapet.backend.domain.dto.auth.CustomOAuth2User;
import com.ssafy.fittapet.backend.domain.dto.health.HealthSleepRequest;
import com.ssafy.fittapet.backend.domain.dto.health.HealthStepRequest;
import com.ssafy.fittapet.backend.domain.entity.Health;
import com.ssafy.fittapet.backend.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<?> createStepCnt(@AuthenticationPrincipal CustomOAuth2User customOAuth2User,
            @RequestBody HealthStepRequest healthStepDto) {
        User loginUser = authService.getLoginUser(customOAuth2User.getUsername());

        Health stepCntCurrentTime = healthService.saveStepCntCurrentTime(
                healthStepDto.getStepCnt(), loginUser);

        return new ResponseEntity<>(stepCntCurrentTime.getId(), HttpStatus.CREATED);
    }

    @PostMapping("/sleep")
    public ResponseEntity<?> createSleepTime(@AuthenticationPrincipal CustomOAuth2User customOAuth2User,
            @RequestBody HealthSleepRequest healthSleepDto) {
        User loginUser = authService.getLoginUser(customOAuth2User.getUsername());
        Health sleepTimeCurrentTime = healthService.saveSleepTimeCurrentTime(
                healthSleepDto.getSleepTime(), loginUser);

        return new ResponseEntity<>(sleepTimeCurrentTime.getId(), HttpStatus.CREATED);
    }

    @GetMapping("/walk")
    public ResponseEntity<?> getStepCntCurrentTime(@AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        User loginUser = authService.getLoginUser(customOAuth2User.getUsername());
        Health healthCurrentTime = healthService.findHealthCurrentTime(loginUser);

        return new ResponseEntity<>(healthCurrentTime.getStepCnt(), HttpStatus.OK);

    }

    @GetMapping("/sleep")
    public ResponseEntity<?> getSleepTimeCurrentTime(@AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        User loginUser = authService.getLoginUser(customOAuth2User.getUsername());
        Health healthCurrentTime = healthService.findHealthCurrentTime(loginUser);

        return new ResponseEntity<>(healthCurrentTime.getSleepTime(), HttpStatus.OK);
    }


}
