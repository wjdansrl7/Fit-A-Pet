package com.ssafy.fittapet.backend.domain.repository.guild_quest;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.fittapet.backend.domain.dto.guild.GuildQuestInfoResponse;
import com.ssafy.fittapet.backend.domain.dto.guild.QGuildQuestInfoResponse;
import com.ssafy.fittapet.backend.domain.entity.QGuildQuest;
import com.ssafy.fittapet.backend.domain.entity.QQuest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GuildQuestCustomRepositoryImpl implements GuildQuestCustomRepository {
    private final JPAQueryFactory queryFactory;


    @Override
    public GuildQuestInfoResponse findQuestInfoByGuildId(Long guildId) {
        QGuildQuest guildQuest = QGuildQuest.guildQuest;
        QQuest quest = QQuest.quest;

        return queryFactory
                .select(new QGuildQuestInfoResponse(
                        guildQuest.id,
                        quest.questName,
                        quest.questContent
                ))
                .from(guildQuest)
                .join(guildQuest.quest, quest)
                .where(guildQuest.guild.id.eq(guildId))
                .fetchOne();
    }
}
