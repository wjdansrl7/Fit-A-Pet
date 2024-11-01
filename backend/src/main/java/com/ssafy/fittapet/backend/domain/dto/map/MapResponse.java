package com.ssafy.fittapet.backend.domain.dto.map;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MapResponse {
    private Long groupId;
    private String groupName;
    private Long groupPosition;

    @Builder
    @QueryProjection
    public MapResponse(Long groupId, String groupName, Long groupPosition) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupPosition = groupPosition;
    }
}
