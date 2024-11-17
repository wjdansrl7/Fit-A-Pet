package com.ssafy.fittapet.backend.domain.dto.guild;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class GuildMemberInfoResponse {
    private Long userId;
    private String userName;
    private Long petId;
    private Boolean questStatus;

    @Builder
    @QueryProjection
    public GuildMemberInfoResponse(Long userId, String userName, Long petId, Boolean questStatus) {
        this.userId = userId;
        this.userName = userName;
        this.petId = petId;
        this.questStatus = questStatus;
    }
}
