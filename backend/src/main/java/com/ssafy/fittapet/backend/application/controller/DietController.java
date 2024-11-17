package com.ssafy.fittapet.backend.application.controller;

import com.ssafy.fittapet.backend.application.service.auth.AuthService;
import com.ssafy.fittapet.backend.application.service.diet.DietService;
import com.ssafy.fittapet.backend.domain.dto.auth.CustomOAuth2User;
import com.ssafy.fittapet.backend.domain.dto.diet.DietResponse;
import com.ssafy.fittapet.backend.domain.dto.health.HealthDietRequest;
import com.ssafy.fittapet.backend.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/diets")
public class DietController {
    private final AuthService authService;
    private final DietService dietService;

    @PostMapping
    public ResponseEntity<?> createDietData(
            @RequestBody HealthDietRequest healthDietRequest,
            @AuthenticationPrincipal CustomOAuth2User customOAuth2User
    ){
        User loginUser = authService.getLoginUser(customOAuth2User.getId());
        dietService.createDietData(healthDietRequest, loginUser);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getDietInfo(
            @AuthenticationPrincipal CustomOAuth2User customOAuth2User
    ) {
        User loginUser = authService.getLoginUser(customOAuth2User.getId());
        DietResponse dietCurrentTime = dietService.getDailyDietData(loginUser);

        return new ResponseEntity<>(dietCurrentTime, HttpStatus.OK);
    }



}
