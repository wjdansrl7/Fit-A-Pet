package com.ssafy.fittapet.backend.domain.dto.map;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.ssafy.fittapet.backend.domain.dto.map.QMapGuildQuestDTO is a Querydsl Projection type for MapGuildQuestDTO
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMapGuildQuestDTO extends ConstructorExpression<MapGuildQuestDTO> {

    private static final long serialVersionUID = -897973057L;

    public QMapGuildQuestDTO(com.querydsl.core.types.Expression<Long> userId, com.querydsl.core.types.Expression<Long> guildId, com.querydsl.core.types.Expression<Long> questId, com.querydsl.core.types.Expression<Long> userQuestStatusId) {
        super(MapGuildQuestDTO.class, new Class<?>[]{long.class, long.class, long.class, long.class}, userId, guildId, questId, userQuestStatusId);
    }

}

