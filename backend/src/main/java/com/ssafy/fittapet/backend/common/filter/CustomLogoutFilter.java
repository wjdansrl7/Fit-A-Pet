package com.ssafy.fittapet.backend.common.filter;

import com.ssafy.fittapet.backend.common.util.JWTUtil;
import com.ssafy.fittapet.backend.domain.entity.Blacklist;
import com.ssafy.fittapet.backend.domain.entity.RefreshToken;
import com.ssafy.fittapet.backend.domain.repository.auth.BlacklistRepository;
import com.ssafy.fittapet.backend.domain.repository.auth.RefreshRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

/**
 * 로그아웃 경로로 POST 요청시 실행되는 필터
 * 현재 등록돼 있는 RefreshToken 삭제하고
 * 블랙리스트 등록
 */
@Slf4j
public class CustomLogoutFilter extends GenericFilter {

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final BlacklistRepository blacklistRepository;

    public CustomLogoutFilter(JWTUtil jwtUtil, BlacklistRepository blacklistRepository, RefreshRepository refreshRepository) {
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
        this.blacklistRepository = blacklistRepository;
    }

    @Value("${refresh-token.milli-second}")
    private Long refreshExpiredMs;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        //path and method verify
        String requestUri = request.getRequestURI();
        if (!requestUri.matches("^\\/logout$")) {
            filterChain.doFilter(request, response);
            return;
        }

        String requestMethod = request.getMethod();
        if (!requestMethod.equals("POST")) {
            filterChain.doFilter(request, response);
            return;
        }

        //get refresh token
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refreshToken")) {
                log.info("cookie name: {}", cookie.getName());
                refresh = cookie.getValue();
            }
        }

        //refresh null check
        if (refresh == null) {
            log.info("refreshToken is null");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        //expired check
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            log.info("refreshToken expired");

            //response body
            PrintWriter writer = response.getWriter();
            writer.print("refreshToken expired");

            //response status code
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        //refresh 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refresh);
        if (!category.equals("refresh")) {
            log.info("invalid refreshToken");

            //response body
            PrintWriter writer = response.getWriter();
            writer.print("invalid refreshToken");

            //response status code
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
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
                log.info("mismatch refreshToken");

                //response body
                PrintWriter writer = response.getWriter();
                writer.print("mismatch refreshToken");

                //response status code
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        } else {

            //response body
            PrintWriter writer = response.getWriter();
            writer.print("not exist refreshToken");

            //response status code
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        Blacklist blacklist = Blacklist.builder()
                .refreshToken(refresh)
                .expiration(refreshExpiredMs)
                .build();

        //로그아웃 진행
        //Refresh 토큰 제거, 블랙리스트 등록
        refreshRepository.deleteById(userId);
        blacklistRepository.save(blacklist);

        //access 토큰 Cookie 값 0
        Cookie cookie = new Cookie("accessToken", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        //refresh 토큰 Cookie 값 0
        Cookie cookie2 = new Cookie("refreshToken", null);
        cookie2.setMaxAge(0);
        cookie2.setPath("/");
        response.addCookie(cookie2);

        response.setStatus(HttpServletResponse.SC_OK);
    }
}
