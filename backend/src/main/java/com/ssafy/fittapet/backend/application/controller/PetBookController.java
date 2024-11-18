package com.ssafy.fittapet.backend.application.controller;

import com.ssafy.fittapet.backend.application.service.auth.AuthService;
import com.ssafy.fittapet.backend.application.service.petbook.PetBookService;
import com.ssafy.fittapet.backend.domain.dto.auth.CustomOAuth2User;
import com.ssafy.fittapet.backend.domain.dto.petbook.PetBookResponse;
import com.ssafy.fittapet.backend.domain.dto.petbook.PetMainResponse;
import com.ssafy.fittapet.backend.domain.dto.petbook.PetNicknameRequest;
import com.ssafy.fittapet.backend.domain.entity.PetBook;
import com.ssafy.fittapet.backend.domain.entity.User;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/petbooks")
public class PetBookController {

    private final PetBookService petBookService;
    private final AuthService authService;

    @GetMapping
    public ResponseEntity<?> getAllPetBooks(@AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        User loginUser = authService.getLoginUser(customOAuth2User.getId());

        List<PetBook> petBooks = petBookService.findAllPetBooks(loginUser);
        List<PetBookResponse> petBookResponseDtos = new ArrayList<>();

        for (PetBook petBook : petBooks) {
            boolean isMain = loginUser.getPetMainId().equals(petBook.getId());

            PetBookResponse petBookResponseDto = PetBookResponse.builder()
                    .petBookId(petBook.getId())
                    .petNickname(petBook.getPetNickname())
                    .petType(petBook.getPet().getPetType())
                    .petStatus(petBook.getPet().getPetStatus())
                    .petLevel(petBook.getPetLevel())
                    .petPercent(petBook.getNextLevelPercentage())
                    .createdAt(petBook.getCreatedAt())
                    .isMain(isMain)
                    .build();
            petBookResponseDtos.add(petBookResponseDto);
        }
        return new ResponseEntity<>(petBookResponseDtos, HttpStatus.OK);
    }

    @GetMapping("/level")
    public ResponseEntity<?> getPetMainStatus(@AuthenticationPrincipal CustomOAuth2User customOAuth2User) {

        User loginUser = authService.getLoginUser(customOAuth2User.getId());
        Long petMainId = loginUser.getPetMainId();

        PetBook petBook = petBookService.findPetBookById(petMainId, loginUser);

        PetMainResponse petMainResponseDto = PetMainResponse.builder()
                .petNickname(petBook.getPetNickname())
                .petLevel(petBook.getPetLevel())
                .petPercent(petBook.getNextLevelPercentage())
                .build();

        return new ResponseEntity<>(petMainResponseDto, HttpStatus.OK);
    }

    @GetMapping("/{petBook-id}")
    public ResponseEntity<?> getPetBook(@PathVariable("petBook-id") Long petBookId, @AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        User loginUser = authService.getLoginUser(customOAuth2User.getId());
        PetBook petBook = petBookService.findPetBookById(petBookId, loginUser);
        boolean isMain = loginUser.getPetMainId().equals(petBook.getId());

        PetBookResponse petBookResponseDto = PetBookResponse.builder()
                .petBookId(petBook.getId())
                .petNickname(petBook.getPetNickname())
                .petType(petBook.getPet().getPetType())
                .petStatus(petBook.getPet().getPetStatus())
                .petLevel(petBook.getPetLevel())
                .petPercent(petBook.getNextLevelPercentage())
                .createdAt(petBook.getCreatedAt())
                .isMain(isMain)
                .build();

        return new ResponseEntity<>(petBookResponseDto, HttpStatus.OK);
    }

    @GetMapping("/main")
    public ResponseEntity<?> getMainPet(@AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        User loginUser = authService.getLoginUser(customOAuth2User.getId());

        PetBook petBook = petBookService.findPetBookById(loginUser.getPetMainId(), loginUser);

        PetBookResponse petBookResponseDto = PetBookResponse.builder()
                .petBookId(petBook.getId())
                .petNickname(petBook.getPetNickname())
                .petType(petBook.getPet().getPetType())
                .petStatus(petBook.getPet().getPetStatus())
                .petLevel(petBook.getPetLevel())
                .petPercent(petBook.getNextLevelPercentage())
                .createdAt(petBook.getCreatedAt()).build();

        return new ResponseEntity<>(petBookResponseDto, HttpStatus.OK);
    }

    @PostMapping("/main/{petBookId}")
    public ResponseEntity<?> updateMainPet(@PathVariable("petBookId") Long petBookId,
                                           @AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        User loginUser = authService.getLoginUser(customOAuth2User.getId());
        loginUser.updatePetMainId(petBookId);
        authService.updateMainPet(petBookId, loginUser);

        return new ResponseEntity<>(petBookId, HttpStatus.OK);

    }

    @PostMapping("/{petBookId}/nickname")
    public ResponseEntity<?> createPetNickname(@PathVariable("petBookId") Long petBookId,
                                               @RequestBody PetNicknameRequest petNicknameRequestDto,
                                               @AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        User loginUser = authService.getLoginUser(customOAuth2User.getId());
        PetBook petBook = petBookService.findPetBookById(petBookId, loginUser);
        petBookService.updatePetNickname(petNicknameRequestDto.getPetNickname(), petBook);

        return new ResponseEntity<>(petBook.getId(), HttpStatus.OK);
    }
}
