package com.ssafy.fittapet.backend.common.validator;

import com.ssafy.fittapet.backend.domain.entity.Guild;
import com.ssafy.fittapet.backend.domain.repository.guild.GuildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GuildValidator {
    private final GuildRepository guildRepository;

    public Optional<Guild> isExist(Long guildId){
        return guildRepository.findById(guildId);
    }

    public boolean isNameUnique(String guildName){
        return guildRepository.findByGuildName(guildName)==null;
    }

    public boolean isGuildLeader(Long guildId, Long userId){
        Guild guild = guildRepository.findById(guildId).orElse(null);
        return guild.getGuildLeader().getId().equals(userId);
    }
}
