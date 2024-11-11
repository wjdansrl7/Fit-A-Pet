package com.ssafy.fittapet.backend.application.controller;

import com.ssafy.fittapet.backend.application.service.auth.AuthServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

    /**
     * 테스트 용 메소드
     * todo 나중에 삭제
     */
    @GetMapping("/test")
    public String testGet() {
        log.info("test");
        return "test";
    }
}
