package com.ssafy.fittapet.backend.domain.dto.group;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
public class GroupInfoResponse {
    private String groupName;
    private Long groupLeaderId;

    @Builder
    @QueryProjection
    public GroupInfoResponse(String groupName, Long groupLeaderId) {
        this.groupName = groupName;
        this.groupLeaderId = groupLeaderId;
    }
}
