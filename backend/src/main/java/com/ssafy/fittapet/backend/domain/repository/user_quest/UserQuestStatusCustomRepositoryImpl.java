package com.ssafy.fittapet.backend.domain.repository.user_quest;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.fittapet.backend.domain.dto.quest.QQuestResponse;
import com.ssafy.fittapet.backend.domain.dto.quest.QuestResponse;
import com.ssafy.fittapet.backend.domain.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.ssafy.fittapet.backend.domain.entity.QGuildQuest.guildQuest;
import static com.ssafy.fittapet.backend.domain.entity.QQuest.quest;
import static com.ssafy.fittapet.backend.domain.entity.QUserQuestStatus.userQuestStatus;

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

    /**
     * todo BatchSize 활용
     */
    @Override
    public Optional<UserQuestStatus> findByUserQuestStatusWithQuest(Long completeQuestId) {

        return Optional.ofNullable(queryFactory
                .selectFrom(userQuestStatus)
                .join(userQuestStatus.guildQuest, guildQuest).fetchJoin()
                .join(guildQuest.quest, quest).fetchJoin()
                .where(userQuestStatus.id.eq(completeQuestId))
                .fetchOne());
    }
}
