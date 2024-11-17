package com.ssafy.fittapet.backend.domain.dto.petbook;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.fittapet.backend.common.constant.entity_field.PetStatus;
import com.ssafy.fittapet.backend.common.constant.entity_field.PetType;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
public class PetBookResponse {

    private Long petBookId;
    private String petNickname;
    private String petType;
    private String petStatus;
    private Integer petPercent;
    private Integer petLevel;
    private boolean isMain;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private String createdAt;
    @Builder
    public PetBookResponse(Long petBookId, String petNickname, PetType petType,
                           PetStatus petStatus, Integer petPercent, Integer petLevel, LocalDateTime createdAt, boolean isMain) {
        this.petBookId = petBookId;
        this.petNickname = petNickname;
        this.petType = (petType != null) ? petType.getValue() : null;
        this.petStatus = (petStatus != null) ? petStatus.getValue() : null;
        this.petPercent = petPercent;
        this.petLevel = petLevel;
        this.createdAt = createdAt.toLocalDate().toString();
        this.isMain = isMain;
    }

}
