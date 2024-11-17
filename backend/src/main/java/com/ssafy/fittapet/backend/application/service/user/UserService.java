package com.ssafy.fittapet.backend.application.service.user;

import com.ssafy.fittapet.backend.domain.dto.auth.UserDto;

public interface UserService {

    UserDto findByUserUniqueName(String userUniqueName);
}
