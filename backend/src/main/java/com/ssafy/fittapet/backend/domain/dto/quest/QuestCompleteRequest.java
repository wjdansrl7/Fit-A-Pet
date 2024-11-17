package com.ssafy.fittapet.backend.domain.dto.quest;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuestCompleteRequest {

    private Long completeQuestId;
}
