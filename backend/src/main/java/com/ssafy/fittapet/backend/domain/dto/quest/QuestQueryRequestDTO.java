package com.ssafy.fittapet.backend.domain.dto.quest;

import com.ssafy.fittapet.backend.common.constant.entity_field.QuestCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestQueryRequestDTO {

    QuestCategory questCategory;
}
