package com.ssafy.fittapet.backend.domain.entity;

import com.ssafy.fittapet.backend.domain.dto.health.HealthDietRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Diet extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diet_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Double calorie;
    private Double protein;
    private Double proteinRatio;
    private Double fat;
    private Double fatRatio;
    private Double carbo;
    private Double carboRatio;

    private Double sodium;
    private Double sodiumRatio;
    private Double sugar;
    private Double sugarRatio;

    @Column(name = "trans_fat")
    private Double transFat;
    private Double transFatRatio;

    @Column(name = "saturated_fat")
    private Double saturatedFat;
    private Double saturatedFatRatio;

    private Double cholesterol;
    private Double cholesterolRatio;

    @Builder
    public static Diet fromRequest(HealthDietRequest healthDietRequest, User user) {
        return Diet.builder()
                .user(user)
                .calorie(healthDietRequest.getCalorie())
                .protein(healthDietRequest.getProtein())
                .proteinRatio(Math.round((healthDietRequest.getProtein() / 60) * 100 * 10) / 10.0)
                .fat(healthDietRequest.getFat())
                .fatRatio(Math.round((healthDietRequest.getFat() / 50) * 100 * 10) / 10.0)
                .carbo(healthDietRequest.getCarbo())
                .carboRatio( Math.round((healthDietRequest.getCarbo() / 328) * 100 * 10) / 10.0)
                .sodium(healthDietRequest.getSodium())
                .sodiumRatio(Math.round((healthDietRequest.getSodium() / 2000) * 100 * 10) / 10.0)
                .sugar(healthDietRequest.getSugar())
                .sugarRatio(Math.round((healthDietRequest.getSugar() / 100) * 100 * 10) / 10.0)
                .transFat(healthDietRequest.getTransFat())
                .transFatRatio( Math.round((healthDietRequest.getTransFat() / 15) * 100 * 10) / 10.0)
                .saturatedFat(healthDietRequest.getSaturatedFat())
                .saturatedFatRatio(Math.round((healthDietRequest.getSaturatedFat() / 2.2) * 100 * 10) / 10.0)
                .cholesterol(healthDietRequest.getCholesterol())
                .cholesterolRatio(Math.round((healthDietRequest.getCholesterol() / 300) * 100 * 10) / 10.0)
                .build();
    }
}
