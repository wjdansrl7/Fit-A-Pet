package com.ssafy.fittapet.backend.domain.dto.petbook;

import lombok.Builder;
import lombok.Data;

@Data
public class PetMainResponse {
    private String petNickname;
    private Integer petLevel;
    private Integer petPercent;

    @Builder
    public PetMainResponse(String petNickname, Integer petLevel, Integer petPercent) {
        this.petNickname = petNickname;
        this.petLevel = petLevel;
        this.petPercent = petPercent;
    }
}
