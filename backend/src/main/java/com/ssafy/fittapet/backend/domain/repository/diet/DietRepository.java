package com.ssafy.fittapet.backend.domain.repository.diet;

import com.ssafy.fittapet.backend.domain.entity.Diet;
import com.ssafy.fittapet.backend.domain.entity.User;
import java.time.LocalDateTime;
import org.springframework.data.repository.CrudRepository;

public interface DietRepository extends CrudRepository<Diet, Long>, DietCustomRepository {

    Diet findByUserAndCreatedAt(User user, LocalDateTime createdAt);
}
