package com.ssafy.fittapet.backend.domain.dto.quest;

import com.ssafy.fittapet.backend.common.constant.entity_field.QuestType;
import lombok.Builder;
import lombok.Getter;

/**
 * 퀘스트 완료 요청
 * todo 불필요한 거 빼기
 */
@Getter
@Builder
public class QuestCompleteRequestDTO {

    private Long completeQuestId;
}
