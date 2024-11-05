package com.ssafy.fittapet.backend.domain.repository.guild;

import com.ssafy.fittapet.backend.domain.entity.Guild;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuildRepository extends JpaRepository<Guild, Long>, GuildCustomRepository {
    Guild findByGuildName(String guildName);
}
