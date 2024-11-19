package com.ssafy.fittapet.backend.domain.dto.guild;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class GuildQuestInfoResponse {

    private Long guildQuestId;
    private String guildQuestName;
    private String guildQuestContent;

    @Builder
    @QueryProjection
    public GuildQuestInfoResponse(Long guildQuestId, String guildQuestName, String guildQuestContent) {
        this.guildQuestId = guildQuestId;
        this.guildQuestName = guildQuestName;
        this.guildQuestContent = guildQuestContent;
    }
}
