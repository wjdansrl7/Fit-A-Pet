package com.ssafy.fittapet.backend.application.service.auth;

import com.ssafy.fittapet.backend.domain.dto.auth.TokenDTO;
import com.ssafy.fittapet.backend.domain.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<?> reissueToken(HttpServletRequest request);

    TokenDTO loginWithKakao(String kakaoAccessToken);

    User getLoginUser(Long userId);
}
