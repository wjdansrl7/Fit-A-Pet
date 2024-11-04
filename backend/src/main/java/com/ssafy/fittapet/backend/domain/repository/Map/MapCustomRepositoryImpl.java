package com.ssafy.fittapet.backend.domain.repository.Map;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.fittapet.backend.domain.dto.map.MapResponse;
import com.ssafy.fittapet.backend.domain.dto.map.QMapResponse;
import com.ssafy.fittapet.backend.domain.entity.QGuild;
import com.ssafy.fittapet.backend.domain.entity.QMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Repository
@RequestMapping
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
}
