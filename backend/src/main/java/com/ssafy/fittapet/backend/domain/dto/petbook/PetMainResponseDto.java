package com.ssafy.fittapet.backend.domain.dto.petbook;

import lombok.Builder;
import lombok.Data;

@Data
public class PetMainResponseDto {
    private String petNickname;
    private Integer petLevel;
    private Integer petPercent;

    @Builder
    public PetMainResponseDto(String petNickname, Integer petLevel, Integer petPercent) {
        this.petNickname = petNickname;
        this.petLevel = petLevel;
        this.petPercent = petPercent;
    }

}
