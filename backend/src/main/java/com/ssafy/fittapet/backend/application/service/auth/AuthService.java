package com.ssafy.fittapet.backend.application.service.auth;

import com.ssafy.fittapet.backend.domain.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<?> reissueTokens(HttpServletRequest request);

    ResponseEntity<?> loginWithKakao(String kakaoAccessToken);

    User getLoginUser(String username);

    void updateMainPet(Long petBookId, User loginUser);
}
