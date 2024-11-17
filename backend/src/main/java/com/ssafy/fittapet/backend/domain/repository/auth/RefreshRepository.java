package com.ssafy.fittapet.backend.domain.repository.auth;

import com.ssafy.fittapet.backend.domain.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshRepository extends CrudRepository<RefreshToken, Long> {
}
