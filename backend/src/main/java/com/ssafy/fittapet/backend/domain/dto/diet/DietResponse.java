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
    private Double carboRatio;
    private Double protein;
    private Double proteinRatio;

    private Double fat;
    private Double fatRatio;

    private Double sodium;
    private Double sodiumRatio;
    private Double sugar;
    private Double sugarRatio;
    private Double transFat;
    private Double transFatRatio;

    private Double saturatedFat;
    private Double saturatedFatRatio;

    private Double cholesterol;
    private Double cholesterolRatio;

    private Boolean isCalorieEnough;
    private Boolean isCalorieEnoughRatio;

    private Boolean isCarboEnough;
    private Boolean isCarboEnoughRatio;

    private Boolean isProteinEnough;
    private Boolean isProteinEnoughRatio;

    private Boolean isFatEnough;
    private Boolean isFatEnoughRatio;

    @Builder
    @QueryProjection
    public DietResponse(Double calorie, Double carbo, Double protein, Double fat,
            Double sodium, Double sugar, Double transFat, Double saturatedFat, Double cholesterol) {
        this.calorie = calorie;
        this.carbo = carbo;
        this.carboRatio = Math.round((carbo / 328) * 100 * 10) / 10.0;
        this.protein = protein;
        this.proteinRatio = Math.round((protein / 60) * 100 * 10) / 10.0;
        this.fat = fat;
        this.fatRatio = Math.round((fat / 50) * 100 * 10) / 10.0;
        this.sodium = sodium;
        this.sodiumRatio = Math.round((sodium / 2000) * 100 * 10) / 10.0;
        this.sugar = sugar;
        this.sugarRatio = Math.round((sugar / 100) * 100 * 10) / 10.0;
        this.transFat = transFat;
        this.transFatRatio = Math.round((transFat / 15) * 100 * 10) / 10.0;
        this.saturatedFat = saturatedFat;
        this.saturatedFatRatio = Math.round((saturatedFat / 2.2) * 100 * 10) / 10.0; // 트랜스지방
        this.cholesterol = cholesterol;
        this.cholesterolRatio = Math.round((cholesterol / 300) * 100 * 10) / 10.0;

        this.isCalorieEnough = calorie>=2000? true : false;
        this.isCarboEnough = carbo>=328? true : false;
        this.isProteinEnough = protein>=60? true : false;
        this.isFatEnough = fat<50? true : false;
    }
}
