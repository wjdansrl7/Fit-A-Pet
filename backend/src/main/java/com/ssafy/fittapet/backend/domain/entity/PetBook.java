package com.ssafy.fittapet.backend.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class PetBook extends BaseEntity{

    @Builder
    public PetBook(User user, Pet pet, Integer petExp, String petNickname) {
        this.user = user;
        this.pet = pet;
        this.petExp = petExp;
        this.petNickname = petNickname;
    }

    @Id
    @Column(name="pet_book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    private Integer petExp;

    private Integer petLevel = 1;

    private String petNickname;

    public void updatePet(Pet pet) {
        this.pet = pet;
    }

    public void updatePetNickname(String petNickname) {
        this.petNickname = petNickname;
    }

    public void levelUp(Integer expGained) {
        this.petExp += expGained;
        if (this.petExp >= getRequiredExpForNextLevel()) {
            this.petLevel++;
            this.petExp = 0;
        }
    }

    // 다음 레벨까지 남은 경험치 비율 계산 메서드
    public Integer getNextLevelPercentage() {
        int requiredExp = getRequiredExpForNextLevel();
        return (int) ((this.petExp / (double) requiredExp) * 100);
    }

    // 진화에 필요한 경험치를 구하는 메서드
    private int getRequiredExpForNextLevel() {
        return this.petLevel * 500;
    }

    // 진화가 필요한지 확인하는 메서드
    public boolean needsEvolution() {
        return this.petLevel.equals(pet.getEvolutionLevel());
    }




}
