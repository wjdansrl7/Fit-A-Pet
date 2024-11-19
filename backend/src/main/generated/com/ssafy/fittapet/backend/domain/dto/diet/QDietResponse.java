package com.ssafy.fittapet.backend.domain.dto.diet;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.ssafy.fittapet.backend.domain.dto.diet.QDietResponse is a Querydsl Projection type for DietResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QDietResponse extends ConstructorExpression<DietResponse> {

    private static final long serialVersionUID = 1665878838L;

    public QDietResponse(com.querydsl.core.types.Expression<Double> calorie, com.querydsl.core.types.Expression<Double> carbo, com.querydsl.core.types.Expression<Double> protein, com.querydsl.core.types.Expression<Double> fat, com.querydsl.core.types.Expression<Double> sodium, com.querydsl.core.types.Expression<Double> sugar, com.querydsl.core.types.Expression<Double> transFat, com.querydsl.core.types.Expression<Double> saturatedFat, com.querydsl.core.types.Expression<Double> cholesterol) {
        super(DietResponse.class, new Class<?>[]{double.class, double.class, double.class, double.class, double.class, double.class, double.class, double.class, double.class}, calorie, carbo, protein, fat, sodium, sugar, transFat, saturatedFat, cholesterol);
    }

}

