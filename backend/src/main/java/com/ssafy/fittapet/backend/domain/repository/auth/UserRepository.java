package com.ssafy.fittapet.backend.domain.repository.auth;

import com.ssafy.fittapet.backend.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserNickname(String username);
}
