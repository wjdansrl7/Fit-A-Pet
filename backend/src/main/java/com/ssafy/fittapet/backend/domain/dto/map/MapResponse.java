package com.ssafy.fittapet.backend.domain.dto.map;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MapResponse {
    private Long guildId;
    private String guildName;
    private Long guildPosition;

    @Builder
    @QueryProjection
    public MapResponse(Long guildId, String guildName, Long guildPosition) {
        this.guildId = guildId;
        this.guildName = guildName;
        this.guildPosition = guildPosition;
    }
}
