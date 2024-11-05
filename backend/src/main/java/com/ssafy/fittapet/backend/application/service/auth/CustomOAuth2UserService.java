package com.ssafy.fittapet.backend.application.service.auth;

import com.ssafy.fittapet.backend.common.constant.Role;
import com.ssafy.fittapet.backend.common.constant.UserTier;
import com.ssafy.fittapet.backend.domain.dto.auth.*;
import com.ssafy.fittapet.backend.domain.entity.User;
import com.ssafy.fittapet.backend.domain.repository.auth.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

/**
 * OAuth2 사용자 정보 처리 서비스
 * 1. Authorization Code 를 활용해 인증 서버로부터 accessToken 발급
 * 2. accessToken 으로 리소스 서버로부터 유저 정보 획득 후 처리 -> SecurityContextHolder 사용자 인증 정보 등록
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("pass CustomOAuth2UserService loadUser");

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("oAuth2User: {}", oAuth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response;

        switch (registrationId) {
            case "kakao" -> {
                log.info("registrationId: kakao");
                oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
            }
            case "naver" -> {
                log.info("registrationId: naver");
                oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
            }
            case "google" -> {
                log.info("registrationId: google");
                oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
            }
            default -> {
                return null;
            }
        }

        //리소스 서버에서 발급 받은 정보로 사용자를 특정할 아이디값 만들기
        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();
        User existData = userRepository.findByUserName(username);

        if (existData == null) { // 유저 정보가 존재하지 않으면

            User user = User.builder()
                    .userNickname(username)
                    .userName(oAuth2Response.getName())
                    .provider(oAuth2Response.getProvider())
                    .providerId(oAuth2Response.getProviderId())
                    .userTier(UserTier.EASY)
                    .role(Role.USER)
                    .build();

            userRepository.save(user); // 유저 엔티티 생성 후 저장

            UserDTO userDTO = UserDTO.builder()
                    .userId(user.getId())
                    .userNickname(username)
                    .role(String.valueOf(Role.USER))
                    .build();

            return new CustomOAuth2User(userDTO);

        } else { // 유저 정보가 있으면

            UserDTO userDTO = UserDTO.builder()
                    .userId(existData.getId())
                    .userNickname(username)
                    .role(String.valueOf(existData.getRole()))
                    .build();

            return new CustomOAuth2User(userDTO);
        }
    }
}