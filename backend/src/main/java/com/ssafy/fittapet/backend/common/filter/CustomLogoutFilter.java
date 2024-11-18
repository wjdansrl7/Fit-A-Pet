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
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

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

        String requestUri = request.getRequestURI();
        if (!requestUri.equals("/auth/logout")) {
            filterChain.doFilter(request, response);
            return;
        }

        String requestMethod = request.getMethod();
        if (!requestMethod.equals("POST")) {
            filterChain.doFilter(request, response);
            return;
        }

        String refresh = null;
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            refresh = authorizationHeader.substring(7);
        }

        if (refresh == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            PrintWriter writer = response.getWriter();
            writer.print("refreshToken expired");

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String category = jwtUtil.getCategory(refresh);
        if (!category.equals("refresh")) {
            PrintWriter writer = response.getWriter();
            writer.print("invalid refreshToken");

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Long userId = jwtUtil.getUserId(refresh);
        Optional<RefreshToken> optionalRefreshToken = refreshRepository.findById(userId);
        if (optionalRefreshToken.isPresent()) {

            RefreshToken savedRefreshToken = optionalRefreshToken.get();

            if (!savedRefreshToken.getToken().equals(refresh)) {

                PrintWriter writer = response.getWriter();
                writer.print("mismatch refreshToken");

                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        } else {

            PrintWriter writer = response.getWriter();
            writer.print("not exist refreshToken");

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        Blacklist blacklist = Blacklist.builder()
                .refreshToken(refresh)
                .expiration(refreshExpiredMs)
                .build();

        refreshRepository.deleteById(userId);
        blacklistRepository.save(blacklist);

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        PrintWriter writer = response.getWriter();
        writer.print("Logged out successfully");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
