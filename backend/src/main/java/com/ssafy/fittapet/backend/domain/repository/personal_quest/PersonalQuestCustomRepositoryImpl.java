package com.ssafy.fittapet.backend.domain.repository.personal_quest;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.fittapet.backend.common.constant.entity_field.QuestCategory;
import com.ssafy.fittapet.backend.domain.dto.quest.QQuestResponse;
import com.ssafy.fittapet.backend.domain.dto.quest.QuestResponse;
import com.ssafy.fittapet.backend.domain.entity.QPersonalQuest;
import com.ssafy.fittapet.backend.domain.entity.QQuest;
import com.ssafy.fittapet.backend.domain.entity.QUser;
import com.ssafy.fittapet.backend.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PersonalQuestCustomRepositoryImpl implements PersonalQuestCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<QuestResponse> findByCategoryAndUser(QuestCategory category, User loginUser) {
        QPersonalQuest personalQuest = QPersonalQuest.personalQuest;
        QQuest quest = QQuest.quest;
        QUser user = QUser.user;

        return queryFactory
                .select(
                        new QQuestResponse(
                               personalQuest.id,
                               quest,
                               personalQuest.questStatus
                        ))
                .from(personalQuest)
                .join(personalQuest.quest, quest)
                .join(personalQuest.user, user)
                .where(personalQuest.user.id.eq(loginUser.getId())
                        .and(quest.questCategory.eq(category)))
                .fetch();
    }
}
