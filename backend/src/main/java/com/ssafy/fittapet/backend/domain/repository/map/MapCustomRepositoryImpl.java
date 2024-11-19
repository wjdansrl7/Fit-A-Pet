package com.ssafy.fittapet.backend.domain.repository.map;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.fittapet.backend.domain.dto.map.MapGuildQuestDTO;
import com.ssafy.fittapet.backend.domain.dto.map.MapResponse;
import com.ssafy.fittapet.backend.domain.dto.map.QMapGuildQuestDTO;
import com.ssafy.fittapet.backend.domain.dto.map.QMapResponse;
import com.ssafy.fittapet.backend.domain.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MapCustomRepositoryImpl implements MapCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MapResponse> findAllByUserId(Long userId) {
        QMap map = QMap.map;
        QGuild guild = QGuild.guild;

        return queryFactory
                .select(new QMapResponse(
                        map.guild.id,
                        map.guild.guildName,
                        map.guildPosition
                ))
                .from(map)
                .join(map.guild, guild)
                .where(map.user.id.eq(userId))
                .fetch();
    }

    @Override
    public List<MapGuildQuestDTO> findAllMGQByUserId(Long userId, Quest quest) {
        QMap map = QMap.map;
        QGuildQuest guildQuest = QGuildQuest.guildQuest;
        QUserQuestStatus userQuestStatus = QUserQuestStatus.userQuestStatus;

        return queryFactory
                .select(new QMapGuildQuestDTO(
                        map.user.id,
                        map.guild.id,
                        guildQuest.quest.id,
                        userQuestStatus.id
                ))
                .from(map)
                .leftJoin(guildQuest).fetchJoin().on(guildQuest.guild.eq(map.guild))
                .leftJoin(userQuestStatus).fetchJoin().on(userQuestStatus.guildQuest.eq(guildQuest))
                .where(map.user.id.eq(userId).and(guildQuest.quest.eq(quest)))
                .fetch();
    }
}
