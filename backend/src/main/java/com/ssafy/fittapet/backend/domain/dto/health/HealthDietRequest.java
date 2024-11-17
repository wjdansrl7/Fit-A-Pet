package com.ssafy.fittapet.backend.domain.dto.health;

import lombok.Data;

@Data
public class HealthDietRequest {
    private Double calorie;
    private Double carbo;
    private Double protein;
    private Double fat;
}
