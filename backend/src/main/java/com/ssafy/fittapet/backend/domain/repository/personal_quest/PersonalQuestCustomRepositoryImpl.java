package com.ssafy.fittapet.backend.domain.repository.personal_quest;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.fittapet.backend.common.constant.entity_field.QuestCategory;
import com.ssafy.fittapet.backend.domain.dto.quest.QQuestResponse;
import com.ssafy.fittapet.backend.domain.dto.quest.QuestResponse;
import com.ssafy.fittapet.backend.domain.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.ssafy.fittapet.backend.domain.entity.QPersonalQuest.personalQuest;
import static com.ssafy.fittapet.backend.domain.entity.QQuest.quest;
import static com.ssafy.fittapet.backend.domain.entity.QUser.user;

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

    @Override
    public Optional<PersonalQuest> findByUserAndQuest(Long userId, Long questId) {

        return Optional.ofNullable(queryFactory
                .selectFrom(personalQuest)
                .join(personalQuest.user, user).fetchJoin()
                .join(personalQuest.quest, quest).fetchJoin()
                .where(user.id.eq(userId)
                        .and(quest.id.eq(questId)))
                .fetchOne());
    }
}
