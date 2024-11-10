package com.ssafy.fittapet.backend.domain.repository.quest;

import com.ssafy.fittapet.backend.common.constant.entity_field.QuestCategory;
import com.ssafy.fittapet.backend.domain.dto.quest.QuestQueryResponseDTO;

import java.util.List;

public interface QuestRepositoryCustom {

    List<QuestQueryResponseDTO> findByCategory(QuestCategory category);
}
