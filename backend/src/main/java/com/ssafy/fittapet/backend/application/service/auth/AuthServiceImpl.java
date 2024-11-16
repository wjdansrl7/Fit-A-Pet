package com.ssafy.fittapet.backend.application.service.auth;

import com.ssafy.fittapet.backend.application.service.petbook.PetBookService;
import com.ssafy.fittapet.backend.common.constant.entity_field.QuestType;
import com.ssafy.fittapet.backend.common.constant.entity_field.Role;
import com.ssafy.fittapet.backend.common.constant.entity_field.UserTier;
import com.ssafy.fittapet.backend.common.util.JWTUtil;
import com.ssafy.fittapet.backend.domain.dto.auth.*;
import com.ssafy.fittapet.backend.domain.entity.*;
import com.ssafy.fittapet.backend.domain.repository.auth.BlacklistRepository;
import com.ssafy.fittapet.backend.domain.repository.auth.RefreshRepository;
import com.ssafy.fittapet.backend.domain.repository.auth.UserRepository;
import com.ssafy.fittapet.backend.domain.repository.personal_quest.PersonalQuestRepository;
import com.ssafy.fittapet.backend.domain.repository.quest.QuestRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final BlacklistRepository blacklistRepository;
    private final UserRepository userRepository;
    private final QuestRepository questRepository;
    private final PersonalQuestRepository personalQuestRepository;
    private final PetBookService petBookService;

    @Value("${access-token.milli-second}")
    private Long accessExpiredMs;

    @Value("${refresh-token.milli-second}")
    private Long refreshExpiredMs;

    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String kakaoUserInfoEndpoint;

    /**
     * 토큰 재발급 메소드
     */
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

            //저장된 토큰과 입력된 토큰 비교
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

        //Access 및 Refresh 토큰 생성
        String newAccess = jwtUtil.createJwt("access", userId, username, role, accessExpiredMs);
        String newRefresh = jwtUtil.createJwt("refresh", userId, username, role, refreshExpiredMs);

        //refresh update
        addRefreshEntity(userId, newRefresh, refreshExpiredMs);

        //token return
        log.info("tokens 재발급");
        TokenDTO tokens = TokenDTO.builder()
                .accessToken(newAccess)
                .refreshToken(newRefresh)
                .build();

        return ResponseEntity.ok(tokens);
    }

    /**
     * 카카오 로그인 메소드
     * AccessToken -> 사용자 정보 GET
     * todo 신규 유저 퀘스트 연관 추가
     * TOOD: 여기에 새로 생성된 펫 등록 추가 필요
     */
    public ResponseEntity<?> loginWithKakao(String kakaoAccessToken) {

        log.info("loginWithKakao");

        //사용자 정보 가져오기
        SignupResponseDto customUserDetails = getUserInfoFromKakao(kakaoAccessToken);

        if (customUserDetails == null) {
            return null; // 오류 처리용으로 null 반환
        }

        String username = customUserDetails.getUserUniqueName();
        Long userId = customUserDetails.getUserId();
        User loginUser = userRepository.findByUserUniqueName(username);

        log.info("redis start");

        //Access 및 Refresh 토큰 생성
        String access = jwtUtil.createJwt("access", userId, username, String.valueOf(Role.USER), accessExpiredMs);
        String refresh = jwtUtil.createJwt("refresh", userId, username, String.valueOf(Role.USER), refreshExpiredMs);

        log.info("redis end");

        //refresh update
        addRefreshEntity(userId, refresh, refreshExpiredMs);

        PetBook petBook = petBookService.selectPetBook(loginUser.getPetMainId(), loginUser);

        customUserDetails.setAccessToken(access);
        customUserDetails.setRefreshToken(refresh);
        customUserDetails.setPetType(petBook.getPet().getPetType().getValue());
        customUserDetails.setPetStatus(petBook.getPet().getPetStatus().getValue());


        return ResponseEntity.ok(customUserDetails);
    }

    /**
     * 유저 아이디 기반 유저 정보 리턴
     * todo DTO 리턴
     */
    public User getLoginUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));
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

    /**
     * 카카오 유저 정보 읽어오기
     */
    private SignupResponseDto getUserInfoFromKakao(String accessToken) {

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

            log.info("existData: {}", existData);
            log.info("username: {}", username);

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

                petBookService.createPetBook(user);

                addPersonalQuests(user);

                log.info("user saved");
//                return new CustomOAuth2User(toUserDTO(user));
//                return new SignupResponseDto(toUserDTO(user));
                return toUserDto(user, true);
            } else {
                log.info("user exist");
//                return new CustomOAuth2User(toUserDTO(existData));
                return toUserDto(existData, false);
            }
        } catch (Exception e) {
            log.error("AuthService getUserInfoFromKakao error: {}", e.getMessage());
            return null;
        }
    }

    private SignupResponseDto toUserDto(User user, boolean shouldShowModal) {
        return SignupResponseDto.builder()
                .userId(user.getId())
                .userUniqueName(user.getUserUniqueName())
                .role(String.valueOf(user.getRole()))
                .shouldShowModal(shouldShowModal)
                .build();
    }

//    private UserDTO toUserDTO(User user) {
//        return UserDTO.builder()
//                .userId(user.getId())
//                .userUniqueName(user.getUserUniqueName())
//                .role(String.valueOf(user.getRole()))
//                .build();
//    }

    public void updateMainPet(Long petBookId, User loginUser) {
        loginUser.updatePetMainId(petBookId);
        userRepository.save(loginUser);
    }

    private void addPersonalQuests(User user) {

        List<Quest> quests = questRepository.findAllByQuestType(QuestType.PERSONAL);
        List<PersonalQuest> personalQuests = quests.stream()
                .map(quest ->
                        PersonalQuest.builder()
                                .user(user)
                                .quest(quest)
                                .questStatus(false)
                                .build()
                )
                .toList();

        personalQuestRepository.saveAll(personalQuests);
    }

    public UserDTO getInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User Not Found"));

        return UserDTO.builder()
                .userId(user.getId())
                .userName(user.getUserName())
                .userUniqueName(user.getUserUniqueName())
                .role(String.valueOf(user.getRole()))
                .userTier(String.valueOf(user.getUserTier()))
                .build();
    }
}
