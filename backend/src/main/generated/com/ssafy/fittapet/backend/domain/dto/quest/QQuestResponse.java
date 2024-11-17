package com.ssafy.fittapet.backend.domain.dto.quest;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.ssafy.fittapet.backend.domain.dto.quest.QQuestResponse is a Querydsl Projection type for QuestResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QQuestResponse extends ConstructorExpression<QuestResponse> {

    private static final long serialVersionUID = -1654082466L;

    public QQuestResponse(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<? extends com.ssafy.fittapet.backend.domain.entity.Quest> quest, com.querydsl.core.types.Expression<Boolean> questStatus) {
        super(QuestResponse.class, new Class<?>[]{long.class, com.ssafy.fittapet.backend.domain.entity.Quest.class, boolean.class}, id, quest, questStatus);
    }

}

