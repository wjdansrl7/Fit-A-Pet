package com.ssafy.fittapet.backend.domain.dto.group;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.ssafy.fittapet.backend.domain.dto.group.QGroupInfoResponse is a Querydsl Projection type for GroupInfoResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QGroupInfoResponse extends ConstructorExpression<GroupInfoResponse> {

    private static final long serialVersionUID = 1343783718L;

    public QGroupInfoResponse(com.querydsl.core.types.Expression<String> groupName, com.querydsl.core.types.Expression<Long> groupLeaderId) {
        super(GroupInfoResponse.class, new Class<?>[]{String.class, long.class}, groupName, groupLeaderId);
    }

}

