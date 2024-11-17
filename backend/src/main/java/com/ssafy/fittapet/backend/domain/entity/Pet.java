package com.ssafy.fittapet.backend.domain.entity;

import com.ssafy.fittapet.backend.common.constant.entity_field.PetStatus;
import com.ssafy.fittapet.backend.common.constant.entity_field.PetType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Pet extends BaseEntity{

    @Builder
    public Pet(PetType petType, PetStatus petStatus, Integer evolutionLevel) {
        this.petStatus = petStatus;
        this.petType = petType;
        this.evolutionLevel = evolutionLevel;
    }

    @Id
    @Column(name="pet_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PetStatus petStatus;

    @Enumerated(EnumType.STRING)
    private PetType petType;

    private Integer evolutionLevel; // 진화 단계별로 고정된 레벨
}
