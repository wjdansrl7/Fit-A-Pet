package com.ssafy.fittapet.backend.application.service.map;

import com.ssafy.fittapet.backend.domain.dto.guild.GuildRequest;
import com.ssafy.fittapet.backend.domain.dto.map.MapResponse;

import java.util.List;

public interface MapService {
    List<MapResponse> getAll();

    void createGuild(GuildRequest guildRequest);

    Boolean joinGuild(String enteringCode, Long guildPosition);

    void leaveGuild(Long guildId);
}
