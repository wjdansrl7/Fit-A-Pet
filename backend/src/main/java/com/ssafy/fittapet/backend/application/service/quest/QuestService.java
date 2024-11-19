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

    Map<String, Object> completePersonalQuest(QuestCompleteRequestDTO dto, Long userId);

    Map<String, Object> completeGuildQuest(QuestCompleteRequestDTO dto, Long userId) throws CustomException;

    List<QuestQueryResponseDTO> queryQuest(QuestQueryRequestDTO dto);
}
