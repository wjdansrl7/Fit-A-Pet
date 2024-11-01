package com.ssafy.fittapet.backend.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserQuestStatus is a Querydsl query type for UserQuestStatus
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserQuestStatus extends EntityPathBase<UserQuestStatus> {

    private static final long serialVersionUID = 330544104L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserQuestStatus userQuestStatus = new QUserQuestStatus("userQuestStatus");

    public final QGroupQuest groupQuest;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath questStatus = createBoolean("questStatus");

    public final QUser user;

    public QUserQuestStatus(String variable) {
        this(UserQuestStatus.class, forVariable(variable), INITS);
    }

    public QUserQuestStatus(Path<? extends UserQuestStatus> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserQuestStatus(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserQuestStatus(PathMetadata metadata, PathInits inits) {
        this(UserQuestStatus.class, metadata, inits);
    }

    public QUserQuestStatus(Class<? extends UserQuestStatus> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.groupQuest = inits.isInitialized("groupQuest") ? new QGroupQuest(forProperty("groupQuest"), inits.get("groupQuest")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

