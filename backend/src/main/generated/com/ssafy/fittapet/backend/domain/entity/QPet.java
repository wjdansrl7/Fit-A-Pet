package com.ssafy.fittapet.backend.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPet is a Querydsl query type for Pet
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPet extends EntityPathBase<Pet> {

    private static final long serialVersionUID = 1832773854L;

    public static final QPet pet = new QPet("pet");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final EnumPath<com.ssafy.fittapet.backend.common.constant.PetStatus> petStatus = createEnum("petStatus", com.ssafy.fittapet.backend.common.constant.PetStatus.class);

    public final EnumPath<com.ssafy.fittapet.backend.common.constant.PetType> petType = createEnum("petType", com.ssafy.fittapet.backend.common.constant.PetType.class);

    public QPet(String variable) {
        super(Pet.class, forVariable(variable));
    }

    public QPet(Path<? extends Pet> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPet(PathMetadata metadata) {
        super(Pet.class, metadata);
    }

}

