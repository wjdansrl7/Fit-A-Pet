package com.ssafy.fittapet.backend.application.service.auth;

import com.ssafy.fittapet.backend.common.constant.entity_field.Role;
import com.ssafy.fittapet.backend.common.constant.entity_field.UserTier;
import com.ssafy.fittapet.backend.common.util.JWTUtil;
import com.ssafy.fittapet.backend.domain.dto.auth.*;
import com.ssafy.fittapet.backend.domain.entity.RefreshToken;
import com.ssafy.fittapet.backend.domain.entity.User;
import com.ssafy.fittapet.backend.domain.repository.auth.BlacklistRepository;
import com.ssafy.fittapet.backend.domain.repository.auth.RefreshRepository;
import com.ssafy.fittapet.backend.domain.repository.auth.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final BlacklistRepository blacklistRepository;
    private final UserRepository userRepository;

    @Value("${access-token.milli-second}")
    private Long accessExpiredMs;

    @Value("${refresh-token.milli-second}")
    private Long refreshExpiredMs;

    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String kakaoUserInfoEndpoint;

    public ResponseEntity<?> reissueToken(HttpServletRequest request) {

        String refresh = null;
        String authorizationHeader = request.getHeader("Authorization");
        log.info("Authorization header: {}", authorizationHeader);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            refresh = authorizationHeader.substring(7);  // "Bearer " 제거하고 토큰 값만 추출
            log.info("refreshToken from header: {}", refresh);
        }

        //refresh null check
        if (refresh == null) {
            return new ResponseEntity<>("refreshToken is null", HttpStatus.BAD_REQUEST);
        }

        //blacklist check
        if (blacklistRepository.existsById(refresh)) {
            return new ResponseEntity<>("refreshToken is blacklisted", HttpStatus.BAD_REQUEST);
        }

        //expired check
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>("refreshToken expired", HttpStatus.BAD_REQUEST);
        }

        //category check (토큰 종류 페이로드에 명시)
        String category = jwtUtil.getCategory(refresh);
        if (!category.equals("refresh")) {
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
        String newAccess = jwtUtil.createJwt("access", userId, username, role, accessExpiredMs);
        String newRefresh = jwtUtil.createJwt("refresh", userId, username, role, refreshExpiredMs);

        //기존 refresh Token 덮어쓰기
        addRefreshEntity(userId, newRefresh, refreshExpiredMs);

        //response
        log.info("token 재발급");
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", newAccess);
        tokens.put("refreshToken", newRefresh);

        return ResponseEntity.ok(tokens);
    }

    /**
     * 가장 마지막 RefreshToken 등록
     */
    private void addRefreshEntity(Long userId, String refresh, Long expiredMs) {

        RefreshToken refreshToken = RefreshToken.builder()
                .userId(userId)
                .token(refresh)
                .expiration(expiredMs)
                .build();

        refreshRepository.save(refreshToken);
    }

    public Map<String, String> loginWithKakao(String kakaoAccessToken) {

        // 사용자 정보 가져오기
        CustomOAuth2User customUserDetails = getUserInfoFromKakao(kakaoAccessToken);

        if (customUserDetails == null) {
            return null; // 오류 처리용으로 null 반환
        }

        String username = customUserDetails.getUsername();
        Long userId = customUserDetails.getId();

        // Access 및 Refresh 토큰 생성
        String access = jwtUtil.createJwt("access", userId, username, String.valueOf(Role.USER), accessExpiredMs);
        String refresh = jwtUtil.createJwt("refresh", userId, username, String.valueOf(Role.USER), refreshExpiredMs);

        // refresh update
        addRefreshEntity(userId, refresh, refreshExpiredMs);

        // 토큰 정보 반환
        return Map.of("accessToken", access, "refreshToken", refresh);
    }

    public CustomOAuth2User getUserInfoFromKakao(String accessToken) {

        log.info("AuthService getUserInfoFromKakao");

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            //GET 사용자 정보 읽어오기
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    kakaoUserInfoEndpoint, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
                    }
            );

            Map<String, Object> attributes = response.getBody();

            if (attributes == null) {
                log.info("attributes null");
                return null;
            }

            OAuth2Response oAuth2Response = new KakaoResponse(attributes);

            // 유저 정보 확인 & 등록
            String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();
            User existData = userRepository.findByUserUniqueName(username);

            if (existData == null) {
                User user = User.builder()
                        .userUniqueName(username)
                        .userName(oAuth2Response.getName())
                        .provider(oAuth2Response.getProvider())
                        .providerId(oAuth2Response.getProviderId())
                        .userTier(UserTier.EASY)
                        .role(Role.USER)
                        .build();

                userRepository.save(user);

                UserDTO userDTO = UserDTO.builder()
                        .userId(user.getId())
                        .userUniqueName(username)
                        .role(String.valueOf(Role.USER))
                        .build();

                return new CustomOAuth2User(userDTO);
            } else {
                UserDTO userDTO = UserDTO.builder()
                        .userId(existData.getId())
                        .userUniqueName(username)
                        .role(String.valueOf(existData.getRole()))
                        .build();

                return new CustomOAuth2User(userDTO);
            }
        } catch (Exception e) {
            log.error("AuthService getUserInfoFromKakao error: {}", e.getMessage());
            return null;
        }
    }

//    @Transactional
//    public ResponseEntity<?> updateTier(TierRequestDTO dto, Long userId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new EntityNotFoundException("User Entity not found"));
//
//        user.updateTier(UserTier.valueOf(dto.getTier()));
//        return ResponseEntity.ok(user.getUserTier().getValue());
//    }
}
