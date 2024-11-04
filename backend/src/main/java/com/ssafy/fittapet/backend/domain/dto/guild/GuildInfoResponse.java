package com.ssafy.fittapet.backend.domain.dto.guild;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GuildInfoResponse {
    private String guildName;
    private Long guildLeaderId;

    @Builder
    @QueryProjection
    public GuildInfoResponse(String guildName, Long guildLeaderId) {
        this.guildName = guildName;
        this.guildLeaderId = guildLeaderId;
    }
}
