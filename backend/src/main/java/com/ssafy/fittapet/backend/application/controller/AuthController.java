package com.ssafy.fittapet.backend.application.controller;

import com.ssafy.fittapet.backend.application.service.auth.AuthServiceImpl;
import com.ssafy.fittapet.backend.domain.dto.auth.CustomOAuth2User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping("/reissue")
    public ResponseEntity<?> reissueToken(HttpServletRequest request) {

        log.info("AuthController reissueToken");
        return authService.reissueToken(request);
    }

    @PostMapping("/kakao")
    public ResponseEntity<?> loginWithKakao(@RequestBody Map<String, String> request) {

        log.info("AuthController loginWithKakao");
        String kakaoAccessToken = request.get("accessToken");

        log.info("AuthController loginWithKakao OK");
        return ResponseEntity.ok(authService.loginWithKakao(kakaoAccessToken));
    }

    @GetMapping("/info")
    public ResponseEntity<?> getInfo(@AuthenticationPrincipal CustomOAuth2User customOAuth2User) {

        log.info("AuthController getInfo");
        return ResponseEntity.ok(authService.getInfo(customOAuth2User.getId()));
    }

    /**
     * 테스트 용 메소드
     * todo 나중에 삭제
     */
    @GetMapping("/test")
    public String testGet() {
        log.info("test success");
        return "test";
    }

    @GetMapping("/test2")
    public String test2Get(@AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        log.info("test2 success {}", customOAuth2User.getUsername());
        return "test";
    }
}
