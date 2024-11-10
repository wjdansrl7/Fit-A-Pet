package com.ssafy.fittapet.backend.application.controller;

import com.ssafy.fittapet.backend.application.service.auth.AuthService;
import com.ssafy.fittapet.backend.application.service.petbook.PetBookService;
import com.ssafy.fittapet.backend.domain.dto.petbook.PetBookCreateDto;
import com.ssafy.fittapet.backend.domain.dto.petbook.PetBookResponseDto;
import com.ssafy.fittapet.backend.domain.dto.petbook.PetMainResponseDto;
import com.ssafy.fittapet.backend.domain.entity.PetBook;
import com.ssafy.fittapet.backend.domain.entity.User;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<?> createPetBook(@RequestBody PetBookCreateDto petBookRequestDto) {
        User loginUser = authService.getLoginUser();
        PetBook petBook = petBookService.createPetBook(petBookRequestDto.getPetNickname(),
                loginUser);

        // 처음 알이 등록되면 로그인한 유저의 대표 캐릭터 변경
        loginUser.updatePetMainId(petBook.getId());


        PetBookResponseDto petBookResponseDto = PetBookResponseDto.builder()
                .petBookId(petBook.getId())
                .petNickname(petBook.getPetNickname())
                .petType(petBook.getPet().getPetType())
                .petStatus(petBook.getPet().getPetStatus())
                .petLevel(petBook.getPetLevel())
                .petPercent(petBook.getNextLevelPercentage())
                .createdAt(petBook.getCreatedAt()).build();

        return new ResponseEntity<>(petBookResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllPetBooks() {
        User loginUser = authService.getLoginUser();

        // 펫 도감에는 유저의 최신 상태의 펫들만 가지고 있음.
        List<PetBook> petBooks = petBookService.selectAllPetBook(loginUser);
        List<PetBookResponseDto> petBookResponseDtos = new ArrayList<>();

        for (PetBook petBook : petBooks) {
            PetBookResponseDto petBookResponseDto = PetBookResponseDto.builder()
                    .petBookId(petBook.getId())
                    .petNickname(petBook.getPetNickname())
                    .petType(petBook.getPet().getPetType())
                    .petStatus(petBook.getPet().getPetStatus())
                    .petLevel(petBook.getPetLevel())
                    .petPercent(petBook.getNextLevelPercentage())
                    .createdAt(petBook.getCreatedAt()).build();
            petBookResponseDtos.add(petBookResponseDto);
        }

        return new ResponseEntity<>(petBookResponseDtos, HttpStatus.OK);
    }

    @GetMapping("/level")
    public ResponseEntity<?> getPetMainStatus() {

        User loginUser = authService.getLoginUser();
        Long petMainId = loginUser.getPetMainId();

        PetBook petBook = petBookService.selectPetBook(petMainId);

        PetMainResponseDto petMainResponseDto = PetMainResponseDto.builder()
                .petNickname(petBook.getPetNickname())
                .petLevel(petBook.getPetLevel())
                .petPercent(petBook.getNextLevelPercentage())
                .build();

        return new ResponseEntity<>(petMainResponseDto, HttpStatus.OK);
    }

    @GetMapping("/{petBook-id}")
    public ResponseEntity<?> getPetBook(@PathVariable("petBook-id") Long petBookId) {
        PetBook petBook = petBookService.selectPetBook(petBookId);

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

    @GetMapping("/main")
    public ResponseEntity<?> getMyPetMain() {
        User loginUser = authService.getLoginUser();

        PetBook petBook = petBookService.selectPetBook(loginUser.getPetMainId());

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
    public ResponseEntity<?> updatePetMain(@PathVariable("petBookId") Long petBookId) {
        User loginUser = authService.getLoginUser();
        loginUser.updatePetMainId(petBookId);

        return new ResponseEntity<>(petBookId, HttpStatus.OK);

    }

    @PostMapping("/{petBookId}/nickname")
    public ResponseEntity<?> createPetNickname(@PathVariable("petBookId") Long petBookId, String petNickname) {
        User loginUser = authService.getLoginUser();

        petBookService.selectPetBook(petBookId).updatePetNickname(petNickname);

        return new ResponseEntity<>(petNickname, HttpStatus.OK);

    }
}
