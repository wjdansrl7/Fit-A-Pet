package com.ssafy.fittapet.backend.domain.repository.quest;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.fittapet.backend.common.constant.entity_field.QuestCategory;
import com.ssafy.fittapet.backend.domain.dto.quest.QQuestQueryResponseDTO;
import com.ssafy.fittapet.backend.domain.dto.quest.QuestQueryResponseDTO;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.ssafy.fittapet.backend.domain.entity.QQuest.quest;

@RequiredArgsConstructor
public class QuestRepositoryCustomImpl implements QuestRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<QuestQueryResponseDTO> findByCategory(QuestCategory category) {
        return queryFactory
                .select(
                        new QQuestQueryResponseDTO(
                                quest
                        ))
                .from(quest)
                .where(category != null ? quest.questCategory.eq(category) : null)
                .fetch();
    }
}
