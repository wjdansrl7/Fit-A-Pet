package com.ssafy.fittapet.backend.application.service.quest;

import com.ssafy.fittapet.backend.common.exception.CustomException;
import com.ssafy.fittapet.backend.domain.dto.quest.QuestCompleteRequest;
import com.ssafy.fittapet.backend.domain.dto.quest.QuestResponse;
import com.ssafy.fittapet.backend.domain.entity.Quest;

import java.util.List;
import java.util.Map;

public interface QuestService {

    List<Quest> searchGuildQuest(String category) throws CustomException;

    Map<String, List<QuestResponse>> getMyQuestList(Long userId);

    Long completePersonalQuest(QuestCompleteRequest dto, Long userId);

    Long completeGuildQuest(QuestCompleteRequest dto, Long userId);
}
