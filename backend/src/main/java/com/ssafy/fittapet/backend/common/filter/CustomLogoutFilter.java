package com.ssafy.fittapet.backend.common.filter;

import com.ssafy.fittapet.backend.common.util.JWTUtil;
import com.ssafy.fittapet.backend.domain.entity.Blacklist;
import com.ssafy.fittapet.backend.domain.entity.RefreshToken;
import com.ssafy.fittapet.backend.domain.repository.auth.BlacklistRepository;
import com.ssafy.fittapet.backend.domain.repository.auth.RefreshRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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

    @Value("${refresh-token.milli-second}")
    private Long refreshExpiredMs;

    public CustomLogoutFilter(JWTUtil jwtUtil, BlacklistRepository blacklistRepository, RefreshRepository refreshRepository) {
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
        this.blacklistRepository = blacklistRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        log.info("pass CustomLogoutFilter");

        //path and method verify
        String requestUri = request.getRequestURI();
        if (!requestUri.matches("^\\/auth\\/logout$")) {
            log.info("jump CustomLogoutFilter");
            filterChain.doFilter(request, response);
            return;
        }

        String requestMethod = request.getMethod();
        if (!requestMethod.equals("POST")) {
            log.info("not POST CustomLogoutFilter");
            filterChain.doFilter(request, response);
            return;
        }

        //get refresh token
        String refresh = null;
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            refresh = authorizationHeader.substring(7);  // "Bearer " 제거하고 토큰 값만 추출
            log.info("refreshToken from header: {}", refresh);
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

        // 세션 무효화
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        //response body
        log.info("Logged out successfully");
        PrintWriter writer = response.getWriter();
        writer.print("Logged out successfully");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
