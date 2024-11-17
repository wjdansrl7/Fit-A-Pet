package com.ssafy.fittapet.backend.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNutrient is a Querydsl query type for Nutrient
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QNutrient extends BeanPath<Nutrient> {

    private static final long serialVersionUID = 891291528L;

    public static final QNutrient nutrient = new QNutrient("nutrient");

    public final NumberPath<Integer> ratio = createNumber("ratio", Integer.class);

    public final NumberPath<Double> serving = createNumber("serving", Double.class);

    public QNutrient(String variable) {
        super(Nutrient.class, forVariable(variable));
    }

    public QNutrient(Path<? extends Nutrient> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNutrient(PathMetadata metadata) {
        super(Nutrient.class, metadata);
    }

}

