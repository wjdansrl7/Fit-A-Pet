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
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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

    public ResponseEntity<?> reissueToken(HttpServletRequest request) {

        String refresh = null;
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            refresh = authorizationHeader.substring(7);
        }

        if (refresh == null) {
            return new ResponseEntity<>("refreshToken is null", HttpStatus.BAD_REQUEST);
        }

        if (blacklistRepository.existsById(refresh)) {
            return new ResponseEntity<>("refreshToken is blacklisted", HttpStatus.BAD_REQUEST);
        }

        if(jwtUtil.isExpired(refresh)) {
            return new ResponseEntity<>("refreshToken expired", HttpStatus.BAD_REQUEST);
        }

        String category = jwtUtil.getCategory(refresh);
        if (!category.equals("refresh")) {
            return new ResponseEntity<>("invalid refreshToken", HttpStatus.BAD_REQUEST);
        }

        Long userId = jwtUtil.getUserId(refresh);
        Optional<RefreshToken> optionalRefreshToken = refreshRepository.findById(userId);
        if (optionalRefreshToken.isPresent()) {
            RefreshToken savedRefreshToken = optionalRefreshToken.get();
            if(!savedRefreshToken.getToken().equals(refresh)){
                return new ResponseEntity<>("mismatch refreshToken", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("not exist refreshToken", HttpStatus.BAD_REQUEST);
        }

        String username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        String newAccess = jwtUtil.createJwt("access", userId, username, role, accessExpiredMs);
        String newRefresh = jwtUtil.createJwt("refresh", userId, username, role, refreshExpiredMs);

        addRefreshEntity(userId, newRefresh, refreshExpiredMs);

        TokenDTO tokens = TokenDTO.builder()
                .accessToken(newAccess)
                .refreshToken(newRefresh)
                .build();

        return ResponseEntity.ok(tokens);
    }

    public ResponseEntity<?> loginWithKakao(String kakaoAccessToken) {

        SignupResponseDto customUserDetails = getUserInfoFromKakao(kakaoAccessToken);

        if (customUserDetails == null) {
            return null;
        }

        String username = customUserDetails.getUserUniqueName();
        Long userId = customUserDetails.getUserId();
        User loginUser = userRepository.findByUserUniqueName(username);

        String access = jwtUtil.createJwt("access", userId, username, String.valueOf(Role.USER), accessExpiredMs);
        String refresh = jwtUtil.createJwt("refresh", userId, username, String.valueOf(Role.USER), refreshExpiredMs);

        addRefreshEntity(userId, refresh, refreshExpiredMs);

        PetBook petBook = petBookService.findPetBookById(loginUser.getPetMainId(), loginUser);

        customUserDetails.setAccessToken(access);
        customUserDetails.setRefreshToken(refresh);
        customUserDetails.setPetType(petBook.getPet().getPetType().getValue());
        customUserDetails.setPetStatus(petBook.getPet().getPetStatus().getValue());

        return ResponseEntity.ok(customUserDetails);
    }

    public User getLoginUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));
    }

    private void addRefreshEntity(Long userId, String refresh, Long expiredMs) {

        RefreshToken refreshToken = RefreshToken.builder()
                .userId(userId)
                .token(refresh)
                .expiration(expiredMs)
                .build();

        refreshRepository.save(refreshToken);
    }

    private SignupResponseDto getUserInfoFromKakao(String accessToken) {

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    kakaoUserInfoEndpoint, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
                    }
            );

            Map<String, Object> attributes = response.getBody();

            if (attributes == null) {
                return null;
            }

            OAuth2Response oAuth2Response = new KakaoResponse(attributes);

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

                petBookService.createPetBook(user);

                addPersonalQuests(user);

                return toUserDto(user, true);
            } else {
                return toUserDto(existData, false);
            }
        } catch (Exception e) {
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
