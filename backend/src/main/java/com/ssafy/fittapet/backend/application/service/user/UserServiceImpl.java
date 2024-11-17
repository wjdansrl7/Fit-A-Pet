package com.ssafy.fittapet.backend.application.service.user;

import com.ssafy.fittapet.backend.domain.dto.auth.UserDto;
import com.ssafy.fittapet.backend.domain.entity.User;
import com.ssafy.fittapet.backend.domain.repository.auth.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto findByUserUniqueName(String userUniqueName) {
        User user = userRepository.findByUserUniqueName(userUniqueName);
        return UserDto.builder()
                .userId(user.getId())
                .userName(user.getUserName())
                .userUniqueName(user.getUserUniqueName())
                .role(String.valueOf(user.getRole()))
                .userTier(String.valueOf(user.getUserTier()))
                .build();
    }
}
