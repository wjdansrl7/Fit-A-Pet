package com.ssafy.fittapet.backend.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGroupQuest is a Querydsl query type for GroupQuest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGroupQuest extends EntityPathBase<GroupQuest> {

    private static final long serialVersionUID = -1533752060L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGroupQuest groupQuest = new QGroupQuest("groupQuest");

    public final QGroup group;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QQuest quest;

    public QGroupQuest(String variable) {
        this(GroupQuest.class, forVariable(variable), INITS);
    }

    public QGroupQuest(Path<? extends GroupQuest> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGroupQuest(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGroupQuest(PathMetadata metadata, PathInits inits) {
        this(GroupQuest.class, metadata, inits);
    }

    public QGroupQuest(Class<? extends GroupQuest> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.group = inits.isInitialized("group") ? new QGroup(forProperty("group"), inits.get("group")) : null;
        this.quest = inits.isInitialized("quest") ? new QQuest(forProperty("quest")) : null;
    }

}

