package com.ssafy.fittapet.backend.application.service.auth;

import com.ssafy.fittapet.backend.common.constant.entity_field.UserTier;
import com.ssafy.fittapet.backend.common.util.JWTUtil;
import com.ssafy.fittapet.backend.domain.dto.auth.CustomOAuth2User;
import com.ssafy.fittapet.backend.domain.dto.auth.TierRequestDTO;
import com.ssafy.fittapet.backend.domain.entity.RefreshToken;
import com.ssafy.fittapet.backend.domain.entity.User;
import com.ssafy.fittapet.backend.domain.repository.auth.BlacklistRepository;
import com.ssafy.fittapet.backend.domain.repository.auth.RefreshRepository;
import com.ssafy.fittapet.backend.domain.repository.auth.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final BlacklistRepository blacklistRepository;
    private final UserRepository userRepository;

    @Value("${refresh-token.milli-second}")
    private Long refreshExpiredMs;

    public User getLoggedInUser() {
        return userRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("임시 유저"));
    }

    public ResponseEntity<?> reissueToken(HttpServletRequest request, HttpServletResponse response) {

        //get refresh token
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refreshToken")) {
                refresh = cookie.getValue();
            }
        }

        //refresh null check
        if (refresh == null) {
            return new ResponseEntity<>("refreshToken null", HttpStatus.BAD_REQUEST);
        }

        //Blacklist 확인
        boolean inBlacklist = blacklistRepository.existsById(refresh);
        if (inBlacklist) {
            return new ResponseEntity<>("refreshToken is blacklisted", HttpStatus.BAD_REQUEST);
        }

        //expired check
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>("refreshToken expired", HttpStatus.BAD_REQUEST);
        }

        // refresh 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refresh);

        if (!category.equals("refresh")) {

            //response status code
            return new ResponseEntity<>("invalid refreshToken", HttpStatus.BAD_REQUEST);
        }

        //DB에 저장되어 있는지 확인
        Long userId = jwtUtil.getUserId(refresh);
        Optional<RefreshToken> optionalRefreshToken = refreshRepository.findById(userId);
        if (optionalRefreshToken.isPresent()) {

            RefreshToken savedRefreshToken = optionalRefreshToken.get();

            // 저장된 토큰과 입력된 토큰 비교
            if (savedRefreshToken.getToken().equals(refresh)) {
                log.info("match refreshToken");
            } else {
                return new ResponseEntity<>("mismatch refreshToken", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("not exist refreshToken", HttpStatus.BAD_REQUEST);
        }

        String username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        //make new JWT
        String newAccess = jwtUtil.createJwt("access", userId, username, role, 600000L);
        String newRefresh = jwtUtil.createJwt("refresh", userId, username, role, 86400000L);

        //기존 Refresh Token 덮어쓰기
        addRefreshEntity(userId, newRefresh, refreshExpiredMs);

        //response
        log.info("token 재발급");
        response.addCookie(createCookie("accessToken", newAccess));
        response.addCookie(createCookie("refreshToken", newRefresh));

        return ResponseEntity.ok("Token reissued successfully");
    }

    @Transactional
    public ResponseEntity<?> updateTier(TierRequestDTO dto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User Entity not found"));

        user.updateTier(UserTier.valueOf(dto.getTier()));

        return ResponseEntity.ok(user.getUserTier().getValue());
    }

    private void addRefreshEntity(Long userId, String refresh, Long expiredMs) {

        RefreshToken refreshToken = RefreshToken.builder()
                .userId(userId)
                .token(refresh)
                .expiration(expiredMs)
                .build();

        refreshRepository.save(refreshToken);
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24 * 60 * 60);
        //cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

//    public ResponseEntity<?> getUser(Long userId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new EntityNotFoundException("User Entity not found"));
//
//
//    }

//    public ResponseEntity<?> getLoginUser(Long userId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new EntityNotFoundException("User Entity not found"));
//
//
//    }
}
