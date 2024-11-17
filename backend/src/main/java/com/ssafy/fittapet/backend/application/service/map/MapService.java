package com.ssafy.fittapet.backend.application.service.map;

import com.ssafy.fittapet.backend.common.exception.CustomException;
import com.ssafy.fittapet.backend.domain.dto.guild.GuildJoinRequest;
import com.ssafy.fittapet.backend.domain.dto.guild.GuildRequest;
import com.ssafy.fittapet.backend.domain.dto.map.MapResponse;

import java.util.List;

public interface MapService {
    List<MapResponse> getAll(String username);

    void createGuild(GuildRequest guildRequest, String username) throws CustomException;

    Boolean joinGuild(GuildJoinRequest guildJoinRequest, String username) throws Exception;

    void leaveGuild(Long guildId, String username) throws CustomException;
}
