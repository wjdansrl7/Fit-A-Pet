package com.ssafy.fittapet.backend.application.service.map;

import com.ssafy.fittapet.backend.common.exception.CustomException;
import com.ssafy.fittapet.backend.domain.dto.guild.GuildJoinRequest;
import com.ssafy.fittapet.backend.domain.dto.guild.GuildRequest;
import com.ssafy.fittapet.backend.domain.dto.map.MapResponse;

import java.util.List;

public interface MapService {

    List<MapResponse> getAll(Long userId);

    void createGuild(GuildRequest guildRequest, Long userId) throws CustomException;

    Boolean joinGuild(GuildJoinRequest guildJoinRequest, Long userId) throws Exception;

    void leaveGuild(Long guildId, Long userId) throws CustomException;
}
