package com.ssafy.fittapet.backend.domain.dto.guild;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;

public class GuildQuestInfoResponse {
    private final Long guildQuestId;
    private final String guildQuestName;
    private final String guildQuestContent;

    @Builder
    @QueryProjection
    public GuildQuestInfoResponse(Long guildQuestId, String guildQuestName, String guildQuestContent) {
        this.guildQuestId = guildQuestId;
        this.guildQuestName = guildQuestName;
        this.guildQuestContent = guildQuestContent;
    }
}
