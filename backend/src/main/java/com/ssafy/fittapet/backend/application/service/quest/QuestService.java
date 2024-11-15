package com.ssafy.fittapet.backend.application.service.quest;

import com.ssafy.fittapet.backend.common.exception.CustomException;
import com.ssafy.fittapet.backend.domain.dto.quest.QuestCompleteRequestDTO;
import com.ssafy.fittapet.backend.domain.dto.quest.QuestQueryRequestDTO;
import com.ssafy.fittapet.backend.domain.dto.quest.QuestQueryResponseDTO;
import com.ssafy.fittapet.backend.domain.dto.quest.QuestResponse;
import com.ssafy.fittapet.backend.domain.entity.Quest;

import java.util.List;
import java.util.Map;

public interface QuestService {
    List<Quest> searchGuildQuest(String category) throws CustomException;

    Map<String, List<QuestResponse>> getMyQuestList(Long userId);

    Long completePersonalQuest(QuestCompleteRequestDTO dto, Long userId);

    Long completeGuildQuest(QuestCompleteRequestDTO dto, Long userId);

    List<QuestQueryResponseDTO> queryQuest(QuestQueryRequestDTO dto);
}
