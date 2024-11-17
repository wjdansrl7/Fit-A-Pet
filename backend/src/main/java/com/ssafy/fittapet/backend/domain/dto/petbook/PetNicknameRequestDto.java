package com.ssafy.fittapet.backend.domain.dto.petbook;

import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * packageName    : com.ssafy.fittapet.backend.domain.dto.petbook
 * fileName       : PetNicknameRequestDto
 * author         : moongi
 * date           : 11/11/24
 * description    :
 */
@Data
public class PetNicknameRequestDto {
    @Size(min = 1,  max = 5, message = "닉네임은 최대 5글자까지 가능합니다.")
    private String petNickname;
}
