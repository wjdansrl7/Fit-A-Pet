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
        this.calorie = Math.round(calorie * 10) / 10.0;
        this.carbo = Math.round(carbo * 10) / 10.0;
        this.carboRatio = Math.round((carbo / 328) * 100 * 10) / 10.0;
        this.protein = Math.round(protein * 10) / 10.0;
        this.proteinRatio = Math.round((protein / 60) * 100 * 10) / 10.0;
        this.fat = Math.round(fat * 10) / 10.0;
        this.fatRatio = Math.round((fat / 50) * 100 * 10) / 10.0;
        this.sodium = Math.round(sodium * 10) / 10.0;
        this.sodiumRatio = Math.round((sodium / 2000) * 100 * 10) / 10.0;
        this.sugar = Math.round(sugar * 10) / 10.0;
        this.sugarRatio = Math.round((sugar / 100) * 100 * 10) / 10.0;
        this.transFat = Math.round(transFat * 10) / 10.0;
        this.transFatRatio = Math.round((transFat / 2.2) * 100 * 10) / 10.0;
        this.saturatedFat = Math.round(saturatedFat * 10) / 10.0;
        this.saturatedFatRatio = Math.round((saturatedFat / 15) * 100 * 10) / 10.0;
        this.cholesterol = Math.round(cholesterol * 10) / 10.0;
        this.cholesterolRatio = Math.round((cholesterol / 300) * 100 * 10) / 10.0;

        this.isCalorieEnough = calorie>=2000? true : false;
        this.isCarboEnough = carbo>=328? true : false;
        this.isProteinEnough = protein>=60? true : false;
        this.isFatEnough = fat<50? true : false;
    }
}
