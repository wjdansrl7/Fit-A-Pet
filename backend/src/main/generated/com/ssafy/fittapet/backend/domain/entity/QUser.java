package com.ssafy.fittapet.backend.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 981576684L;

    public static final QUser user = new QUser("user");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final ListPath<PersonalQuest, QPersonalQuest> personalQuests = this.<PersonalQuest, QPersonalQuest>createList("personalQuests", PersonalQuest.class, QPersonalQuest.class, PathInits.DIRECT2);

    public final StringPath provider = createString("provider");

    public final StringPath providerId = createString("providerId");

    public final EnumPath<com.ssafy.fittapet.backend.common.constant.entity_field.Role> role = createEnum("role", com.ssafy.fittapet.backend.common.constant.entity_field.Role.class);

    public final StringPath userName = createString("userName");

    public final EnumPath<com.ssafy.fittapet.backend.common.constant.entity_field.UserTier> userTier = createEnum("userTier", com.ssafy.fittapet.backend.common.constant.entity_field.UserTier.class);

    public final StringPath userUniqueName = createString("userUniqueName");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

