package com.ssafy.fitapet.backend.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPetBook is a Querydsl query type for PetBook
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPetBook extends EntityPathBase<PetBook> {

    private static final long serialVersionUID = 1482815719L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPetBook petBook = new QPetBook("petBook");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isMain = createBoolean("isMain");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final QPet pet;

    public final NumberPath<Integer> petExp = createNumber("petExp", Integer.class);

    public final StringPath petNickname = createString("petNickname");

    public final QUser user;

    public QPetBook(String variable) {
        this(PetBook.class, forVariable(variable), INITS);
    }

    public QPetBook(Path<? extends PetBook> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPetBook(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPetBook(PathMetadata metadata, PathInits inits) {
        this(PetBook.class, metadata, inits);
    }

    public QPetBook(Class<? extends PetBook> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.pet = inits.isInitialized("pet") ? new QPet(forProperty("pet")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

