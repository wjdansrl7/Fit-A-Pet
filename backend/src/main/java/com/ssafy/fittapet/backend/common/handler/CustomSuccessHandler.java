package com.ssafy.fittapet.backend.common.handler;

import com.ssafy.fittapet.backend.common.util.JWTUtil;
import com.ssafy.fittapet.backend.domain.dto.auth.CustomOAuth2User;
import com.ssafy.fittapet.backend.domain.entity.RefreshToken;
import com.ssafy.fittapet.backend.domain.repository.auth.RefreshRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

/**
 * 로그인 성공 핸들러, 유저 정보 기반으로 jwt token 발급
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    @Value("${access-token.milli-second}")
    private Long accessExpiredMs;

    @Value("${refresh-token.milli-second}")
    private Long refreshExpiredMs;

    @Value("${frontend.server.url}")
    private String url;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {

        log.info("pass CustomSuccessHandler onAuthenticationSuccess");

        //OAuth2User
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();

        String username = customUserDetails.getUsername();
        Long userId = customUserDetails.getId();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        // 토큰 생성
        String accessToken = jwtUtil.createJwt("access", userId, username, role, accessExpiredMs);
        String refreshToken = jwtUtil.createJwt("refresh", userId, username, role, refreshExpiredMs);

        addRefreshEntity(userId,refreshToken,refreshExpiredMs);

        log.info("token 생성");
        response.addCookie(createCookie("accessToken", accessToken));
        response.addCookie(createCookie("refreshToken", refreshToken));
//        response.sendRedirect("http://localhost:3000/");
        response.sendRedirect(url);
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60 * 60 * 60);
        //cookie.setSecure(true); //HTTPS
        cookie.setPath("/"); //적용 범위
        cookie.setHttpOnly(true); //프론트 자바스크립트 쿠키 접근 방지

        return cookie;
    }

    private void addRefreshEntity(Long userId, String refresh, Long expiredMs) {

        RefreshToken refreshToken = RefreshToken.builder()
                .userId(userId)
                .token(refresh)
                .expiration(expiredMs)
                .build();

        refreshRepository.save(refreshToken);
    }
}