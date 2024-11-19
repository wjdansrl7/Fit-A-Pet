package com.ssafy.fittapet.backend.domain.dto.guild;

import lombok.Getter;

@Getter
public class GuildJoinRequest {

    String enteringCode;
    Long guildPosition;
}
