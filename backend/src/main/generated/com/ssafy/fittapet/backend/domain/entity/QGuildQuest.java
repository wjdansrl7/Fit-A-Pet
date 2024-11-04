package com.ssafy.fittapet.backend.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGuildQuest is a Querydsl query type for GuildQuest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGuildQuest extends EntityPathBase<GuildQuest> {

    private static final long serialVersionUID = 25743376L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGuildQuest guildQuest = new QGuildQuest("guildQuest");

    public final QGuild guild;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QQuest quest;

    public QGuildQuest(String variable) {
        this(GuildQuest.class, forVariable(variable), INITS);
    }

    public QGuildQuest(Path<? extends GuildQuest> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGuildQuest(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGuildQuest(PathMetadata metadata, PathInits inits) {
        this(GuildQuest.class, metadata, inits);
    }

    public QGuildQuest(Class<? extends GuildQuest> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.guild = inits.isInitialized("guild") ? new QGuild(forProperty("guild"), inits.get("guild")) : null;
        this.quest = inits.isInitialized("quest") ? new QQuest(forProperty("quest")) : null;
    }

}

