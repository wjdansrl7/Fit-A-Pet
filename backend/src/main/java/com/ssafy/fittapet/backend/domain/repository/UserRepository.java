package com.ssafy.fittapet.backend.domain.repository;

import com.ssafy.fittapet.backend.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
