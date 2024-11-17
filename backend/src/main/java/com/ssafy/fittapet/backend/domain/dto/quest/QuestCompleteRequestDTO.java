package com.ssafy.fittapet.backend.domain.dto.quest;

import com.ssafy.fittapet.backend.common.constant.entity_field.QuestType;
import lombok.*;

/**
 * 퀘스트 완료 요청
 * todo 불필요한 거 빼기
 */
@Data
@NoArgsConstructor
public class QuestCompleteRequestDTO {

    private Long completeQuestId;
}
