package com.ssafy.fittapet.backend.domain.repository.user_quest;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.fittapet.backend.domain.dto.quest.QQuestResponse;
import com.ssafy.fittapet.backend.domain.dto.quest.QuestResponse;
import com.ssafy.fittapet.backend.domain.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserQuestStatusCustomRepositoryImpl implements UserQuestStatusCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<QuestResponse> findByUser(User loginUser) {
        QUserQuestStatus userQuestStatus = QUserQuestStatus.userQuestStatus;
        QGuildQuest guildQuest = QGuildQuest.guildQuest;
        QQuest quest = QQuest.quest;

        return queryFactory
                .select(new QQuestResponse(
                        userQuestStatus.id,
                        quest,
                        userQuestStatus.questStatus
                ))
                .from(userQuestStatus)
                .join(userQuestStatus.guildQuest, guildQuest)
                .join(userQuestStatus.guildQuest.quest, quest)
                .where(userQuestStatus.user.eq(loginUser))
                .fetch();
    }
}
