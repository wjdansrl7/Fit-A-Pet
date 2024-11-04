package com.ssafy.fittapet.backend.domain.dto.map;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.ssafy.fittapet.backend.domain.dto.map.QMapResponse is a Querydsl Projection type for MapResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMapResponse extends ConstructorExpression<MapResponse> {

    private static final long serialVersionUID = 1483435218L;

    public QMapResponse(com.querydsl.core.types.Expression<Long> groupId, com.querydsl.core.types.Expression<String> groupName, com.querydsl.core.types.Expression<Long> groupPosition) {
        super(MapResponse.class, new Class<?>[]{long.class, String.class, long.class}, groupId, groupName, groupPosition);
    }

}

