package com.ssafy.fittapet.backend.domain.repository.health;

import com.ssafy.fittapet.backend.domain.entity.Health;
import com.ssafy.fittapet.backend.domain.entity.User;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthRepository extends JpaRepository<Health, Long> {
    Health findByUserAndCreatedAt(User user, LocalDateTime createdAt);

}
