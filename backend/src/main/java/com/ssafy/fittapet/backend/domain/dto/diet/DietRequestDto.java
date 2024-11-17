package com.ssafy.fittapet.backend.domain.dto.diet;

import lombok.Data;

@Data
public class DietRequestDto {
    private Integer calorie;
    private NutrientDto sodium;
    private NutrientDto carbo;
    private NutrientDto totalSugars;
    private NutrientDto fat;
    private NutrientDto saturatedFattyAcid;
    private NutrientDto transFattyAcid;
    private NutrientDto cholesterol;
    private NutrientDto protein;
}
