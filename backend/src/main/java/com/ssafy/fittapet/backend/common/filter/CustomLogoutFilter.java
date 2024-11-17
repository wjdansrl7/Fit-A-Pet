package com.ssafy.fittapet.backend.common.filter;

import com.ssafy.fittapet.backend.application.service.blacklist.BlacklistService;
import com.ssafy.fittapet.backend.application.service.refresh.RefreshService;
import com.ssafy.fittapet.backend.common.util.JWTUtil;
import com.ssafy.fittapet.backend.domain.entity.Blacklist;
import com.ssafy.fittapet.backend.domain.entity.RefreshToken;
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
    private final BlacklistService blacklistService;
    private final RefreshService refreshService;

    @Value("${refresh-token.milli-second}")
    private Long refreshExpiredMs;

    public CustomLogoutFilter(JWTUtil jwtUtil, BlacklistService blacklistService, RefreshService refreshService) {
        this.jwtUtil = jwtUtil;
        this.blacklistService = blacklistService;
        this.refreshService = refreshService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        String requestUri = request.getRequestURI();
        if (!requestUri.matches("^/auth/logout$")) {
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

        if (jwtUtil.isExpired(refresh)) {
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

        String userUniqueName = jwtUtil.getUsername(refresh);
        Optional<RefreshToken> optionalRefreshToken = refreshService.findRefreshTokenById(userUniqueName);
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

        refreshService.deleteRefreshTokenById(userUniqueName);
        blacklistService.saveBlacklist(blacklist);

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        PrintWriter writer = response.getWriter();
        writer.print("Logged out successfully");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
