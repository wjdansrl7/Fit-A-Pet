package com.ssafy.fittapet.backend.domain.repository.auth;

import com.ssafy.fittapet.backend.domain.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshRepository extends CrudRepository<RefreshToken, String> {
}
