package com.ssafy.fittapet.backend.domain.dto.petbook;

import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
public class PetNicknameRequest {
    @Size(min = 1,  max = 5, message = "닉네임은 최대 5글자까지 가능합니다.")
    private String petNickname;
}
