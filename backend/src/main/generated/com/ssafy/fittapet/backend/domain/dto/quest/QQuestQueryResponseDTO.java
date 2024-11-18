package com.ssafy.fittapet.backend.domain.dto.quest;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.ssafy.fittapet.backend.domain.dto.quest.QQuestQueryResponseDTO is a Querydsl Projection type for QuestQueryResponseDTO
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QQuestQueryResponseDTO extends ConstructorExpression<QuestQueryResponseDTO> {

    private static final long serialVersionUID = 1809184531L;

    public QQuestQueryResponseDTO(com.querydsl.core.types.Expression<? extends com.ssafy.fittapet.backend.domain.entity.Quest> quest) {
        super(QuestQueryResponseDTO.class, new Class<?>[]{com.ssafy.fittapet.backend.domain.entity.Quest.class}, quest);
    }

}

