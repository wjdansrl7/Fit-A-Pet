package com.ssafy.fittapet.backend.application.controller;

import com.ssafy.fittapet.backend.application.service.auth.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/reissue")
    public ResponseEntity<?> reissueToken(HttpServletRequest request) {

        log.info("AuthController reissueToken");
        return authService.reissueToken(request);
    }

    @PostMapping("/kakao")
    public ResponseEntity<?> loginWithKakao(@RequestBody Map<String, String> request) {

        log.info("AuthController loginWithKakao");
        String kakaoAccessToken = request.get("accessToken");

        Map<String, String> tokens = authService.loginWithKakao(kakaoAccessToken);

        if (tokens == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("return token is null");
        }

        log.info("AuthController loginWithKakao OK");
        return ResponseEntity.ok(tokens);
    }

    @GetMapping("/test")
    public String testGet() {
        log.info("test");
        return "test";
    }

//    @PutMapping("/tier")
//    public ResponseEntity<?> updateTier(@RequestBody TierRequestDTO dto, @AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
//        log.info("AuthController updateTier");
//        return authService.updateTier(dto, customOAuth2User.getId());
//    }
}
