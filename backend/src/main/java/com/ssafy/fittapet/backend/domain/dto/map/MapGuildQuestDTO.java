package com.ssafy.fittapet.backend.domain.dto.map;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MapGuildQuestDTO {

    private Long userId;
    private Long guildId;
    private Long questId;
    private Long userQuestStatusId;

    @Builder
    @QueryProjection
    public MapGuildQuestDTO(Long userId, Long guildId, Long questId, Long userQuestStatusId) {
        this.userId = userId;
        this.guildId = guildId;
        this.questId = questId;
        this.userQuestStatusId = userQuestStatusId;
    }
}
