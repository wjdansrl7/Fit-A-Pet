package com.ssafy.fittapet.backend.domain.repository.group;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.fittapet.backend.domain.dto.group.GroupInfoResponse;
import com.ssafy.fittapet.backend.domain.dto.group.QGroupInfoResponse;
import com.ssafy.fittapet.backend.domain.dto.map.MapResponse;
import com.ssafy.fittapet.backend.domain.dto.map.QMapResponse;
import com.ssafy.fittapet.backend.domain.entity.QGroup;
import com.ssafy.fittapet.backend.domain.entity.QMap;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping
@RequiredArgsConstructor
public class GroupCustomRepositoryImpl implements GroupCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<MapResponse> findAllByUserId(Long userId) {
        QMap map = QMap.map;
        QGroup group = QGroup.group;

        return queryFactory
                .select(new QMapResponse(
                        map.group.id,
                        map.group.groupName,
                        map.groupPosition
                ))
                .from(map)
                .join(map.group, group)
                .where(map.user.id.eq(userId))
                .fetch();
    }

    @Override
    public GroupInfoResponse findInfoById(Long groupId) {
        QGroup group = QGroup.group;

        return queryFactory
                .select(new QGroupInfoResponse(
                        group.groupName,
                        group.groupLeader.id
                ))
                .from(group)
                .where(group.id.eq(groupId))
                .fetchOne();
    }
}
