package com.ssafy.fittapet.backend.domain.dto.petbook;

import lombok.Data;

@Data
public class PetBookRequestDto {
    public String petType;
    public String petNickname;
}
