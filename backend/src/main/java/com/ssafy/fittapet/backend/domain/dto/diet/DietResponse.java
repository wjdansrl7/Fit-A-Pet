package com.ssafy.fittapet.backend.domain.dto.diet;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

@Data
public class DietResponse {
//    private int calorie;
//    private NutrientDto sodium;
//    private NutrientDto carbo;
//    private NutrientDto totalSugars;
//    private NutrientDto fat;
//    private NutrientDto saturatedFattyAcid;
//    private NutrientDto transFattyAcid;
//    private NutrientDto cholesterol;
//    private NutrientDto protein;

    private Double calorie;
    private Double carbo;
    private Double protein;
    private Double fat;

    private Boolean isCalorieEnough;
    private Boolean isCarboEnough;
    private Boolean isProteinEnough;
    private Boolean isFatEnough;

    @Builder
    @QueryProjection
    public DietResponse(Double calorie, Double carbo, Double protein, Double fat) {
        this.calorie = calorie;
        this.carbo = carbo;
        this.protein = protein;
        this.fat = fat;

        this.isCalorieEnough = calorie>=2000? true : false;
        this.isCarboEnough = carbo>=328? true : false;
        this.isProteinEnough = protein>=60? true : false;
        this.isFatEnough = fat<50? true : false;
    }
}
