package com.ssafy.fittapet.backend.application.controller;

import com.ssafy.fittapet.backend.application.service.auth.AuthService;
import com.ssafy.fittapet.backend.application.service.petbook.PetBookService;
import com.ssafy.fittapet.backend.domain.dto.auth.CustomOAuth2User;
import com.ssafy.fittapet.backend.domain.dto.petbook.PetBookCreateDto;
import com.ssafy.fittapet.backend.domain.dto.petbook.PetBookResponseDto;
import com.ssafy.fittapet.backend.domain.dto.petbook.PetMainResponseDto;
import com.ssafy.fittapet.backend.domain.dto.petbook.PetNicknameRequestDto;
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

    /**
     * todo userId 쓰는거 추후 @AuthenticationPrincipal 변경
     */
//    @PostMapping
//    public ResponseEntity<?> createPetBook(@RequestBody PetBookCreateDto petBookRequestDto,
//                                           @AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
//        User loginUser = authService.getLoginUser(customOAuth2User.getId());
//        PetBook petBook = petBookService.createPetBook(petBookRequestDto.getPetNickname(), loginUser);
//
//        // 이미 모든 알을 가지고 있는 경우
//        if (petBook == null) {
//            return new ResponseEntity<>("이미 모든 펫을 가지고 있습니다.", HttpStatus.OK);
//        }
//
//        PetBookResponseDto petBookResponseDto = PetBookResponseDto.builder()
//                .petBookId(petBook.getId())
//                .petNickname(petBook.getPetNickname())
//                .petType(petBook.getPet().getPetType())
//                .petStatus(petBook.getPet().getPetStatus())
//                .petLevel(petBook.getPetLevel())
//                .petPercent(petBook.getNextLevelPercentage())
//                .createdAt(petBook.getCreatedAt()).build();
//
//        return new ResponseEntity<>(petBookResponseDto, HttpStatus.CREATED);
//    }

    @GetMapping
    public ResponseEntity<?> getAllPetBooks(@AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        User loginUser = authService.getLoginUser(customOAuth2User.getId());

        // 펫 도감에는 유저의 최신 상태의 펫들만 가지고 있음.
        List<PetBook> petBooks = petBookService.selectAllPetBook(loginUser);
        List<PetBookResponseDto> petBookResponseDtos = new ArrayList<>();

        for (PetBook petBook : petBooks) {
            boolean isMain = loginUser.getPetMainId().equals(petBook.getId());

            PetBookResponseDto petBookResponseDto = PetBookResponseDto.builder()
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

        PetBook petBook = petBookService.selectPetBook(petMainId, loginUser);

        PetMainResponseDto petMainResponseDto = PetMainResponseDto.builder()
                .petNickname(petBook.getPetNickname())
                .petLevel(petBook.getPetLevel())
                .petPercent(petBook.getNextLevelPercentage())
                .build();

        return new ResponseEntity<>(petMainResponseDto, HttpStatus.OK);
    }

    @GetMapping("/{petBook-id}")
    public ResponseEntity<?> getPetBook(@PathVariable("petBook-id") Long petBookId, @AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
//        User loginUser = authService.getLoginUser(customOAuth2User.getId());
        User loginUser = authService.getLoginUser(1L);
        PetBook petBook = petBookService.selectPetBook(petBookId, loginUser);
        boolean isMain = loginUser.getPetMainId().equals(petBook.getId());

        PetBookResponseDto petBookResponseDto = PetBookResponseDto.builder()
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
    public ResponseEntity<?> getMyPetMain(@AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        User loginUser = authService.getLoginUser(customOAuth2User.getId());

        PetBook petBook = petBookService.selectPetBook(loginUser.getPetMainId(), loginUser);

        PetBookResponseDto petBookResponseDto = PetBookResponseDto.builder()
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
    public ResponseEntity<?> updatePetMain(@PathVariable("petBookId") Long petBookId,
                                           @AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        User loginUser = authService.getLoginUser(customOAuth2User.getId());
        loginUser.updatePetMainId(petBookId);
        authService.updateMainPet(petBookId, loginUser);

        return new ResponseEntity<>(petBookId, HttpStatus.OK);

    }

    @PostMapping("/{petBookId}/nickname")
    public ResponseEntity<?> createPetNickname(@PathVariable("petBookId") Long petBookId,
                                               @RequestBody PetNicknameRequestDto petNicknameRequestDto,
                                               @AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        User loginUser = authService.getLoginUser(customOAuth2User.getId());

        PetBook petBook = petBookService.selectPetBook(petBookId, loginUser);

        petBookService.updatePetNickname(petNicknameRequestDto.getPetNickname(), petBook);

        return new ResponseEntity<>(petBook.getId(), HttpStatus.OK);

    }
}
