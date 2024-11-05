package com.ssafy.fittapet.backend.common.config;

import com.ssafy.fittapet.backend.common.constant.Role;
import com.ssafy.fittapet.backend.common.constant.UserTier;
import com.ssafy.fittapet.backend.domain.entity.User;
import com.ssafy.fittapet.backend.domain.repository.auth.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DbInit {

    private final UserRepository userRepository;

    @PostConstruct
    void init() {

        List<User> users = new ArrayList<>();

        var user1 = createUser("kim", "김철수", "kim", "kakao", "EASY", "USER");
        var user2 = createUser("lee", "이영희", "lee", "kakao", "NORMAL", "ADMIN");
        var user3 = createUser("park", "박민수", "park", "kakao", "HARD", "USER");
        var user4 = createUser("choi", "최영호", "choi", "kakao", "EASY", "USER");
        var user5 = createUser("jang", "장미희", "jang", "kakao", "NORMAL", "USER");

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);

        userRepository.saveAll(users);
    }

    private User createUser(String userNickname, String userName, String providerId, String provider, String
            userTier, String role) {
        return User.builder()
                .userNickname(userNickname)
                .userName(userName)
                .providerId(providerId)
                .provider(provider)
                .userTier(UserTier.valueOf(userTier))
                .role(Role.valueOf(role))
                .build();
    }
}
