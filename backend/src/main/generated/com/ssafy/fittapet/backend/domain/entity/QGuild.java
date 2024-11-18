package com.ssafy.fittapet.backend.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGuild is a Querydsl query type for Guild
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGuild extends EntityPathBase<Guild> {

    private static final long serialVersionUID = 351240178L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGuild guild = new QGuild("guild");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final QUser guildLeader;

    public final StringPath guildName = createString("guildName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public QGuild(String variable) {
        this(Guild.class, forVariable(variable), INITS);
    }

    public QGuild(Path<? extends Guild> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGuild(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGuild(PathMetadata metadata, PathInits inits) {
        this(Guild.class, metadata, inits);
    }

    public QGuild(Class<? extends Guild> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.guildLeader = inits.isInitialized("guildLeader") ? new QUser(forProperty("guildLeader")) : null;
    }

}

