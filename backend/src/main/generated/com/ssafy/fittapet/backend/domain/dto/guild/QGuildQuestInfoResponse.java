package com.ssafy.fittapet.backend.domain.dto.guild;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.ssafy.fittapet.backend.domain.dto.guild.QGuildQuestInfoResponse is a Querydsl Projection type for GuildQuestInfoResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QGuildQuestInfoResponse extends ConstructorExpression<GuildQuestInfoResponse> {

    private static final long serialVersionUID = 1500505202L;

    public QGuildQuestInfoResponse(com.querydsl.core.types.Expression<Long> guildQuestId, com.querydsl.core.types.Expression<String> guildQuestName, com.querydsl.core.types.Expression<String> guildQuestContent) {
        super(GuildQuestInfoResponse.class, new Class<?>[]{long.class, String.class, String.class}, guildQuestId, guildQuestName, guildQuestContent);
    }

}

