package com.ssafy.fittapet.backend.common.filter;

import com.ssafy.fittapet.backend.common.util.JWTUtil;
import com.ssafy.fittapet.backend.domain.dto.auth.CustomOAuth2User;
import com.ssafy.fittapet.backend.domain.dto.auth.UserDTO;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * JWT accessToken 검증하는 필터
 * 처음 OAuth2 인증 이후 JWT 필터로만 검사한다.
 */
@RequiredArgsConstructor
@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        log.info("pass JWTFilter");
        String path = request.getRequestURI();

        // 재발급 경로는 필터를 건너뛴다
        if (path.equals("/auth/reissue")) {
            log.info("jump JWTFilter");
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = null;
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            accessToken = authorizationHeader.substring(7);  // "Bearer " 제거하고 토큰 값만 추출
            log.info("accessToken from header: {}", accessToken);
        }

        //accessToken 유무
        if (accessToken == null) {
            log.info("accessToken is null");

            // 권한이 필요없는 기능을 위해 다음 필터로 넘김
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰 만료 여부 확인, 만료시 다음 필터로 넘기지 않음
        try {
            jwtUtil.isExpired(accessToken);
        } catch (ExpiredJwtException e) {
            log.info("accessToken expired");

            //response body
            PrintWriter writer = response.getWriter();
            writer.print("accessToken expired");

            //response status code
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // access 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(accessToken);
        if (!category.equals("access")) {
            log.info("invalid accessToken");

            //response body
            PrintWriter writer = response.getWriter();
            writer.print("invalid accessToken");

            //response status code
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        //토큰에서 userId, username, role 획득
        Long userId = jwtUtil.getUserId(accessToken);
        String username = jwtUtil.getUsername(accessToken);
        String role = jwtUtil.getRole(accessToken);

        UserDTO userDTO = UserDTO.builder()
                .userId(userId)
                .userUniqueName(username)
                .role(role)
                .build();

        //UserDetails 회원 정보 객체 담기
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(userDTO);

        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());

        log.info("SecurityContextHolder setAuthentication");

        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
