package com.ssafy.fittapet.backend.domain.repository.guild;

import com.ssafy.fittapet.backend.domain.dto.guild.GuildInfoResponse;
import com.ssafy.fittapet.backend.domain.dto.guild.GuildMemberInfoResponse;
import com.ssafy.fittapet.backend.domain.dto.map.MapResponse;

import java.util.List;

public interface GuildCustomRepository {
    List<MapResponse> findAllByUserId(Long userId);

    GuildInfoResponse findInfoById(Long id);

    List<GuildMemberInfoResponse> findAllMemberByGuild(Long guildId);
}
