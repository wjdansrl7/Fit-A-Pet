package com.ssafy.fittapet.backend.application.controller;

import com.ssafy.fittapet.backend.application.service.auth.AuthServiceImpl;
import com.ssafy.fittapet.backend.domain.dto.auth.CustomOAuth2User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping("/reissue")
    public ResponseEntity<?> createTokens(HttpServletRequest request) {
        return authService.reissueTokens(request);
    }

    @PostMapping("/kakao")
    public ResponseEntity<?> loginWithKakao(@RequestBody Map<String, String> request) {
        return ResponseEntity.ok(authService.loginWithKakao(request.get("accessToken")));
    }

    @GetMapping("/info")
    public ResponseEntity<?> getInfo(@AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        return ResponseEntity.ok(authService.getInfo(customOAuth2User.getId()));
    }
}
