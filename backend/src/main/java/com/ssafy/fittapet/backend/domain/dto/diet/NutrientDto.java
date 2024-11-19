package com.ssafy.fittapet.backend.domain.dto.diet;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class NutrientDto {

    private double serving;
    private Integer ratio;
}
