package com.ssafy.fittapet.backend.domain.dto.guild;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.ssafy.fittapet.backend.domain.dto.guild.QGuildInfoResponse is a Querydsl Projection type for GuildInfoResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QGuildInfoResponse extends ConstructorExpression<GuildInfoResponse> {

    private static final long serialVersionUID = 973572878L;

    public QGuildInfoResponse(com.querydsl.core.types.Expression<String> guildName, com.querydsl.core.types.Expression<Long> guildLeaderId) {
        super(GuildInfoResponse.class, new Class<?>[]{String.class, long.class}, guildName, guildLeaderId);
    }

}

