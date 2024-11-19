package com.ssafy.fittapet.backend.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDiet is a Querydsl query type for Diet
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDiet extends EntityPathBase<Diet> {

    private static final long serialVersionUID = 981060629L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDiet diet = new QDiet("diet");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Double> calorie = createNumber("calorie", Double.class);

    public final NumberPath<Double> carbo = createNumber("carbo", Double.class);

    public final NumberPath<Double> cholesterol = createNumber("cholesterol", Double.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Double> fat = createNumber("fat", Double.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<Double> protein = createNumber("protein", Double.class);

    public final NumberPath<Double> saturatedFat = createNumber("saturatedFat", Double.class);

    public final NumberPath<Double> sodium = createNumber("sodium", Double.class);

    public final NumberPath<Double> sugar = createNumber("sugar", Double.class);

    public final NumberPath<Double> transFat = createNumber("transFat", Double.class);

    public final QUser user;

    public QDiet(String variable) {
        this(Diet.class, forVariable(variable), INITS);
    }

    public QDiet(Path<? extends Diet> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDiet(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDiet(PathMetadata metadata, PathInits inits) {
        this(Diet.class, metadata, inits);
    }

    public QDiet(Class<? extends Diet> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

