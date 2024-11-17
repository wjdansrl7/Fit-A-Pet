package com.ssafy.fittapet.backend.domain.repository.auth;

import com.ssafy.fittapet.backend.domain.entity.Blacklist;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;

public interface BlacklistRepository extends CrudRepository<Blacklist, String> {

    boolean existsById(@NonNull String refreshToken);
}
