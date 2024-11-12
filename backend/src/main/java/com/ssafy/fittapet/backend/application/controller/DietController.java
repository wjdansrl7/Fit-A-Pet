package com.ssafy.fittapet.backend.application.controller;

import com.ssafy.fittapet.backend.application.service.auth.AuthService;
import com.ssafy.fittapet.backend.application.service.diet.DietService;
import com.ssafy.fittapet.backend.domain.dto.diet.DietRequestDto;
import com.ssafy.fittapet.backend.domain.dto.diet.DietResponseDto;
import com.ssafy.fittapet.backend.domain.entity.Diet;
import com.ssafy.fittapet.backend.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/diets")
public class DietController {
    private final AuthService authService;
    private final DietService dietService;

    @PostMapping
    public ResponseEntity<?> createDiet(DietRequestDto dietRequestDto) {
        User loginUser = authService.getLoginUser(1L);

        Diet dietCurrentTime = dietService.createDietCurrentTime(dietRequestDto, loginUser);

        return new ResponseEntity<>(dietCurrentTime.getId(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getDietInfo() {
        User loginUser = authService.getLoginUser(1L);
        DietResponseDto dietCurrentTime = dietService.getDietCurrentTime(loginUser);

        return new ResponseEntity<>(dietCurrentTime, HttpStatus.OK);

    }



}
