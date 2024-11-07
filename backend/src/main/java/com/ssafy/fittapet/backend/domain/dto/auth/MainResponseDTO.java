package com.ssafy.fittapet.backend.domain.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MainResponseDTO {

    private Long userId;
    private String userName;
    private String userTier;
    private String role;
    private Long perId;
    private String perNickName;
    private Long petExp;
}
