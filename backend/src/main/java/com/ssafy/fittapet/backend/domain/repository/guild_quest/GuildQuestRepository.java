package com.ssafy.fittapet.backend.domain.repository.guild_quest;

import com.ssafy.fittapet.backend.domain.entity.GuildQuest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuildQuestRepository extends JpaRepository<GuildQuest, Long>, GuildQuestCustomRepository {

    GuildQuest findByGuildId(Long guildId);
}
