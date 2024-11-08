package com.ssafy.fittapet.backend.domain.repository.map;

import com.ssafy.fittapet.backend.domain.entity.Map;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MapRepository extends JpaRepository<Map, Long>, MapCustomRepository {
    Long countByGuildId(Long guildId);

    void deleteByGuildIdAndUserId(Long guildId, Long userId);

    Map findByUserIdAndGuildPosition(Long userId, Long guildPosition);

    Map findByUserIdAndGuildId(Long userId, Long guildId);
}
