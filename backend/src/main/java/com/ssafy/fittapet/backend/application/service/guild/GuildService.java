package com.ssafy.fittapet.backend.application.service.guild;

import com.ssafy.fittapet.backend.common.exception.CustomException;
import com.ssafy.fittapet.backend.domain.dto.guild.GuildInfoResponse;
import com.ssafy.fittapet.backend.domain.dto.guild.GuildMemberInfoResponse;
import com.ssafy.fittapet.backend.domain.dto.guild.GuildQuestInfoResponse;

import java.util.List;

public interface GuildService {

    String getEnteringCode(Long guildId, String username);

    GuildInfoResponse getGuildInfo(Long guildId) throws CustomException;

    void updateGuildQuest(Long guildId, Long questId) throws CustomException;

    List<GuildMemberInfoResponse> getMemberInfo(Long guildId) throws CustomException;

    GuildQuestInfoResponse getQuestInfo(Long guildId) throws CustomException;
}
