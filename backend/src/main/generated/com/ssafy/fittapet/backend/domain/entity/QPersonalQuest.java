package com.ssafy.fittapet.backend.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPersonalQuest is a Querydsl query type for PersonalQuest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPersonalQuest extends EntityPathBase<PersonalQuest> {

    private static final long serialVersionUID = 1831124865L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPersonalQuest personalQuest = new QPersonalQuest("personalQuest");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QQuest quest;

    public final BooleanPath questStatus = createBoolean("questStatus");

    public final QUser user;

    public QPersonalQuest(String variable) {
        this(PersonalQuest.class, forVariable(variable), INITS);
    }

    public QPersonalQuest(Path<? extends PersonalQuest> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPersonalQuest(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPersonalQuest(PathMetadata metadata, PathInits inits) {
        this(PersonalQuest.class, metadata, inits);
    }

    public QPersonalQuest(Class<? extends PersonalQuest> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.quest = inits.isInitialized("quest") ? new QQuest(forProperty("quest")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

