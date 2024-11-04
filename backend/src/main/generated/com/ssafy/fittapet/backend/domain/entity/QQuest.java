package com.ssafy.fittapet.backend.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QQuest is a Querydsl query type for Quest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQuest extends EntityPathBase<Quest> {

    private static final long serialVersionUID = 360471777L;

    public static final QQuest quest = new QQuest("quest");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.ssafy.fittapet.backend.common.constant.QuestCategory> questCategory = createEnum("questCategory", com.ssafy.fittapet.backend.common.constant.QuestCategory.class);

    public final StringPath questContent = createString("questContent");

    public final StringPath questName = createString("questName");

    public final NumberPath<Long> questReward = createNumber("questReward", Long.class);

    public final EnumPath<com.ssafy.fittapet.backend.common.constant.QuestTier> questTier = createEnum("questTier", com.ssafy.fittapet.backend.common.constant.QuestTier.class);

    public final EnumPath<com.ssafy.fittapet.backend.common.constant.QuestType> questType = createEnum("questType", com.ssafy.fittapet.backend.common.constant.QuestType.class);

    public QQuest(String variable) {
        super(Quest.class, forVariable(variable));
    }

    public QQuest(Path<? extends Quest> path) {
        super(path.getType(), path.getMetadata());
    }

    public QQuest(PathMetadata metadata) {
        super(Quest.class, metadata);
    }

}

