package com.ssafy.fittapet.backend.domain.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Diet extends BaseEntity {

    @Builder
    public Diet(User user, Integer calorie, Nutrient sodium, Nutrient carbo,
            Nutrient totalSugars, Nutrient fat, Nutrient saturatedFattyAcid,
            Nutrient transFattyAcid,
            Nutrient cholesterol, Nutrient protein) {
        this.user = user;
        this.calorie = calorie;
        this.sodium = sodium;
        this.carbo = carbo;
        this.totalSugars = totalSugars;
        this.fat = fat;
        this.saturatedFattyAcid = saturatedFattyAcid;
        this.transFattyAcid = transFattyAcid;
        this.cholesterol = cholesterol;
        this.protein = protein;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diet_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Integer calorie;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "serving", column = @Column(name = "sodium_serving")),
            @AttributeOverride(name = "ratio", column = @Column(name = "sodium_ratio"))
    })
    private Nutrient sodium; // 나트륨

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "serving", column = @Column(name = "carbo_serving")),
            @AttributeOverride(name = "ratio", column = @Column(name = "carbo_ratio"))
    })
    private Nutrient carbo; // 탄수화물

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "serving", column = @Column(name = "total_sugars_serving")),
            @AttributeOverride(name = "ratio", column = @Column(name = "total_sugars_ratio"))
    })
    private Nutrient totalSugars; // 당류

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "serving", column = @Column(name = "fat_serving")),
            @AttributeOverride(name = "ratio", column = @Column(name = "fat_ratio"))
    })
    private Nutrient fat; // 지방

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "serving", column = @Column(name = "saturated_fatty_acid_serving")),
            @AttributeOverride(name = "ratio", column = @Column(name = "saturated_fatty_acid_ratio"))
    })
    private Nutrient saturatedFattyAcid; // 트랜스지방

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "serving", column = @Column(name = "trans_fatty_acid_serving")),
            @AttributeOverride(name = "ratio", column = @Column(name = "trans_fatty_acid_ratio"))
    })
    private Nutrient transFattyAcid; // 포화지방

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "serving", column = @Column(name = "cholesterol_serving")),
            @AttributeOverride(name = "ratio", column = @Column(name = "cholesterol_ratio"))
    })
    private Nutrient cholesterol; // 콜레스테롤

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "serving", column = @Column(name = "protein_serving")),
            @AttributeOverride(name = "ratio", column = @Column(name = "protein_ratio"))
    })
    private Nutrient protein; // 단백질


    public void updateCalorie(Integer calorie) {
        this.calorie = calorie;
    }

    public void updateSodium(Nutrient nutrient) {
        this.sodium = nutrient;
    }

    public void updateCarbo(Nutrient nutrient) {
        this.carbo = nutrient;
    }

    public void updateTotalSugars(Nutrient nutrient) {
        this.totalSugars = nutrient;
    }

    public void updateFat(Nutrient nutrient) {
        this.fat = nutrient;
    }

    public void updateSaturatedFattyAcid(Nutrient nutrient) {
        this.saturatedFattyAcid = nutrient;
    }

    public void updateTransFattyAcid(Nutrient nutrient) {
        this.transFattyAcid = nutrient;
    }

    public void updateCholesterol(Nutrient nutrient) {
        this.cholesterol = nutrient;
    }

    public void updateProtein(Nutrient nutrient) {
        this.protein = nutrient;
    }
}
