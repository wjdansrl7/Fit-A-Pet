package com.ssafy.fittapet.backend.application.controller;

import com.ssafy.fittapet.backend.application.service.petbook.PetBookService;
import com.ssafy.fittapet.backend.domain.dto.petbook.PetBookRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/petbooks")
public class PetBookController {

    private final PetBookService petBookService;
    @GetMapping
    public ResponseEntity<?> createPetBook(@RequestBody PetBookRequestDto petBookRequestDto) {
        return null;
    }

}
