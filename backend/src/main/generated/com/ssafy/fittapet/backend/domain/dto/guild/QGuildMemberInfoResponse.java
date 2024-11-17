package com.ssafy.fittapet.backend.domain.dto.guild;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.ssafy.fittapet.backend.domain.dto.guild.QGuildMemberInfoResponse is a Querydsl Projection type for GuildMemberInfoResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QGuildMemberInfoResponse extends ConstructorExpression<GuildMemberInfoResponse> {

    private static final long serialVersionUID = -299559032L;

    public QGuildMemberInfoResponse(com.querydsl.core.types.Expression<Long> userId, com.querydsl.core.types.Expression<String> userName, com.querydsl.core.types.Expression<Long> petId, com.querydsl.core.types.Expression<Boolean> questStatus) {
        super(GuildMemberInfoResponse.class, new Class<?>[]{long.class, String.class, long.class, boolean.class}, userId, userName, petId, questStatus);
    }

}

