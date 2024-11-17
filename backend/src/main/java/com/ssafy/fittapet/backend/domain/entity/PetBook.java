package com.ssafy.fittapet.backend.domain.entity;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    private Integer petExp;

    private Integer petLevel = 1;

    private String petNickname;

    private boolean issueEgg = false;

    public void updatePet(Pet pet) {
        this.pet = pet;
    }
    public void updatePetNickname(String petNickname) {
        this.petNickname = petNickname;
    }
    public void updateIssueEgg(boolean issueEgg) {
        this.issueEgg = issueEgg;
    }
    public void levelUp(Integer expGained) {
        this.petExp += expGained;
        if (this.petExp >= getRequiredExpForNextLevel()) {
            this.petLevel = this.petExp / 500 + 1;
        }
    }
    public Integer getNextLevelPercentage() {
        int requiredExp = getRequiredExpForNextLevel();
        return (int) ((this.petExp / (double) requiredExp) * 100);
    }
    private int getRequiredExpForNextLevel() {
        return this.petLevel * 500;
    }
    public boolean needsEvolution() {
        return this.petLevel.equals(pet.getEvolutionLevel());
    }




}
