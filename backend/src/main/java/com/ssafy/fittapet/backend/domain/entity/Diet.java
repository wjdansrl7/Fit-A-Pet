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

//    @Builder
//    public Diet(HealthDietRequest healthDietRequest, User user) {
//        this.user = user;
//        this.calorie = healthDietRequest.getCalorie();
//        this.protein = healthDietRequest.getProtein();
//        this.fat = healthDietRequest.getFat();
//        this.carbo = healthDietRequest.getCarbo();
//        this.sodium = healthDietRequest.getSodium();
//        this.sugar = healthDietRequest.getSugar();
//        this.transFat = healthDietRequest.getTransFat();
//        this.saturatedFat = healthDietRequest.getSaturatedFat();
//        this.cholesterol = healthDietRequest.getCholesterol();
//    }

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


//    @Builder
//    public Diet(User user, Integer calorie, Nutrient sodium, Nutrient carbo,
//            Nutrient totalSugars, Nutrient fat, Nutrient saturatedFattyAcid,
//            Nutrient transFattyAcid,
//            Nutrient cholesterol, Nutrient protein) {
//        this.user = user;
//        this.calorie = calorie;
//        this.sodium = sodium;
//        this.carbo = carbo;
//        this.totalSugars = totalSugars;
//        this.fat = fat;
//        this.saturatedFattyAcid = saturatedFattyAcid;
//        this.transFattyAcid = transFattyAcid;
//        this.cholesterol = cholesterol;
//        this.protein = protein;
//    }
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "diet_id")
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    private Integer calorie;
//
//    @Embedded
//    @AttributeOverrides({
//            @AttributeOverride(name = "serving", column = @Column(name = "sodium_serving")),
//            @AttributeOverride(name = "ratio", column = @Column(name = "sodium_ratio"))
//    })
//    private Nutrient sodium; // 나트륨
//
//    @Embedded
//    @AttributeOverrides({
//            @AttributeOverride(name = "serving", column = @Column(name = "carbo_serving")),
//            @AttributeOverride(name = "ratio", column = @Column(name = "carbo_ratio"))
//    })
//    private Nutrient carbo; // 탄수화물
//
//    @Embedded
//    @AttributeOverrides({
//            @AttributeOverride(name = "serving", column = @Column(name = "total_sugars_serving")),
//            @AttributeOverride(name = "ratio", column = @Column(name = "total_sugars_ratio"))
//    })
//    private Nutrient totalSugars; // 당류
//
//    @Embedded
//    @AttributeOverrides({
//            @AttributeOverride(name = "serving", column = @Column(name = "fat_serving")),
//            @AttributeOverride(name = "ratio", column = @Column(name = "fat_ratio"))
//    })
//    private Nutrient fat; // 지방
//
//    @Embedded
//    @AttributeOverrides({
//            @AttributeOverride(name = "serving", column = @Column(name = "saturated_fatty_acid_serving")),
//            @AttributeOverride(name = "ratio", column = @Column(name = "saturated_fatty_acid_ratio"))
//    })
//    private Nutrient saturatedFattyAcid; // 트랜스지방
//
//    @Embedded
//    @AttributeOverrides({
//            @AttributeOverride(name = "serving", column = @Column(name = "trans_fatty_acid_serving")),
//            @AttributeOverride(name = "ratio", column = @Column(name = "trans_fatty_acid_ratio"))
//    })
//    private Nutrient transFattyAcid; // 포화지방
//
//    @Embedded
//    @AttributeOverrides({
//            @AttributeOverride(name = "serving", column = @Column(name = "cholesterol_serving")),
//            @AttributeOverride(name = "ratio", column = @Column(name = "cholesterol_ratio"))
//    })
//    private Nutrient cholesterol; // 콜레스테롤
//
//    @Embedded
//    @AttributeOverrides({
//            @AttributeOverride(name = "serving", column = @Column(name = "protein_serving")),
//            @AttributeOverride(name = "ratio", column = @Column(name = "protein_ratio"))
//    })
//    private Nutrient protein; // 단백질
//
//
//    public void updateCalorie(Integer calorie) {
//        this.calorie = calorie;
//    }
//
//    public void updateSodium(Nutrient nutrient) {
//        this.sodium = nutrient;
//    }
//
//    public void updateCarbo(Nutrient nutrient) {
//        this.carbo = nutrient;
//    }
//
//    public void updateTotalSugars(Nutrient nutrient) {
//        this.totalSugars = nutrient;
//    }
//
//    public void updateFat(Nutrient nutrient) {
//        this.fat = nutrient;
//    }
//
//    public void updateSaturatedFattyAcid(Nutrient nutrient) {
//        this.saturatedFattyAcid = nutrient;
//    }
//
//    public void updateTransFattyAcid(Nutrient nutrient) {
//        this.transFattyAcid = nutrient;
//    }
//
//    public void updateCholesterol(Nutrient nutrient) {
//        this.cholesterol = nutrient;
//    }
//
//    public void updateProtein(Nutrient nutrient) {
//        this.protein = nutrient;
//    }
}
