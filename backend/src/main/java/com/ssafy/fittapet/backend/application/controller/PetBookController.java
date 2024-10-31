package com.ssafy.fittapet.backend.application.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/petbooks")
public class PetBookController {

    @GetMapping
    public ResponseEntity<?> test() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
