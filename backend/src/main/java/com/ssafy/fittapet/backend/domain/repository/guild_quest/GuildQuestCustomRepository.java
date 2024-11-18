package com.ssafy.fittapet.backend.domain.repository.guild_quest;

import com.ssafy.fittapet.backend.domain.dto.guild.GuildQuestInfoResponse;

public interface GuildQuestCustomRepository {
    GuildQuestInfoResponse findQuestInfoByGuildId(Long guildId);
}
