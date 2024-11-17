package com.ssafy.fittapet.backend.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHealth is a Querydsl query type for Health
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHealth extends EntityPathBase<Health> {

    private static final long serialVersionUID = -1982841283L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHealth health = new QHealth("health");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<Integer> sleepTime = createNumber("sleepTime", Integer.class);

    public final NumberPath<Integer> stepCnt = createNumber("stepCnt", Integer.class);

    public final QUser user;

    public QHealth(String variable) {
        this(Health.class, forVariable(variable), INITS);
    }

    public QHealth(Path<? extends Health> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHealth(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHealth(PathMetadata metadata, PathInits inits) {
        this(Health.class, metadata, inits);
    }

    public QHealth(Class<? extends Health> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

