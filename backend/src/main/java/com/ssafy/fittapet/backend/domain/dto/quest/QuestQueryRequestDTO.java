package com.ssafy.fittapet.backend.domain.dto.quest;

import com.ssafy.fittapet.backend.common.constant.entity_field.QuestCategory;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestQueryRequestDTO {

    QuestCategory questCategory;
}
