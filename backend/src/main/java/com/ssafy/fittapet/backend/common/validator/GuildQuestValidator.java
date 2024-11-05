package com.ssafy.fittapet.backend.common.validator;

import com.ssafy.fittapet.backend.domain.repository.guild_quest.GuildQuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GuildQuestValidator {
    private final GuildQuestRepository guildQuestRepository;

}
