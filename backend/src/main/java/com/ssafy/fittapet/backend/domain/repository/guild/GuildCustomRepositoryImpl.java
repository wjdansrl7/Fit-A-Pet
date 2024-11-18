package com.ssafy.fittapet.backend.domain.repository.guild;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.fittapet.backend.domain.dto.guild.GuildInfoResponse;
import com.ssafy.fittapet.backend.domain.dto.guild.GuildMemberInfoResponse;
import com.ssafy.fittapet.backend.domain.dto.guild.QGuildInfoResponse;
import com.ssafy.fittapet.backend.domain.dto.guild.QGuildMemberInfoResponse;
import com.ssafy.fittapet.backend.domain.dto.map.MapResponse;
import com.ssafy.fittapet.backend.domain.dto.map.QMapResponse;
import com.ssafy.fittapet.backend.domain.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GuildCustomRepositoryImpl implements GuildCustomRepository {
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
    public GuildInfoResponse findInfoById(Long guildId) {
        QGuild guild = QGuild.guild;

        return queryFactory
                .select(new QGuildInfoResponse(
                        guild.guildName,
                        guild.guildLeader.id
                ))
                .from(guild)
                .where(guild.id.eq(guildId))
                .fetchOne();
    }

    @Override
    public List<GuildMemberInfoResponse> findAllMemberByGuild(Long guildId) {
        QMap map = QMap.map;
        QGuild guild = QGuild.guild;
        QUser user = QUser.user;
        QPetBook petBook = QPetBook.petBook;
        QUserQuestStatus userQuestStatus = QUserQuestStatus.userQuestStatus;

        return queryFactory
                .selectDistinct(new QGuildMemberInfoResponse(
                        map.user.id,
                        map.user.userName,
                        petBook.pet.id,
                        userQuestStatus.questStatus
                ))
                .from(map)
                .leftJoin(petBook).on(petBook.user.eq(map.user))
                .leftJoin(userQuestStatus).on(userQuestStatus.user.eq(map.user))
                .where(map.guild.id.eq(guildId)
                        .and(petBook.id.eq(map.user.petMainId))
                )
//                        .and(userQuestStatus.guildQuest.guild.id.eq(guildId)))
                .fetch();
    }
}
