package com.ssafy.fittapet.backend.common.validator;

import com.ssafy.fittapet.backend.domain.repository.Map.MapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MapValidator {
    private final MapRepository mapRepository;

    public boolean isUnder6(Long guildId){
        return mapRepository.countByGuildId(guildId)<6;
    }

    public boolean isAlreadyJoined(Long userId, Long guildId){
        return mapRepository.findByUserIdAndGuildId(userId, guildId).equals(null);
    }

    public boolean isAblePosition(Long userId, Long guildPosition){
        return mapRepository.findByUserIdAndGuildPosition(userId, guildPosition).equals(null);
    }
}
