package com.ssafy.fittapet.backend.application.controller;

import com.ssafy.fittapet.backend.application.service.auth.AuthService;
import com.ssafy.fittapet.backend.domain.dto.auth.CustomOAuth2User;
import com.ssafy.fittapet.backend.domain.dto.auth.TierRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

//    @GetMapping("/login")
//    public ResponseEntity<?> getLoginUser(@AuthenticationPrincipal CustomOAuth2User customOAuth2User){
//        return authService.getLoginUser(customOAuth2User.getId());
//    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissueToken(HttpServletRequest request, HttpServletResponse response) {
        return authService.reissueToken(request, response);
    }

//    @GetMapping("/auth/{userId}")
//    public ResponseEntity<?> getUser(@PathVariable Long userId) {
//        return authService.getUser(userId);
//    }

    @PutMapping("/tier")
    public ResponseEntity<?> updateTier(@RequestBody TierRequestDTO dto, @AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        return authService.updateTier(dto, customOAuth2User.getId());
    }

    @GetMapping("/test")
    public String testGet() {
        log.info("test");
        return "test";
    }

    @PostMapping("/test")
    public String testPost() {
        log.info("test");
        return "test";
    }
}
