package com.ssafy.fittapet.backend.application.service.guild;

import com.ssafy.fittapet.backend.domain.dto.guild.GuildInfoResponse;
import com.ssafy.fittapet.backend.domain.dto.guild.GuildRequest;
import com.ssafy.fittapet.backend.domain.dto.map.MapResponse;

import java.util.List;

public interface GuildService {
    List<MapResponse> getAll();

    void createGuild(GuildRequest guildRequest);

    String getEnteringCode(Long guildId);

    Boolean joinGuild(String enteringCode, Long guildPosition);

    void leaveGuild(Long guildId);

    GuildInfoResponse getGuildInfo(Long guildId);
}
