package com.ssafy.fittapet.backend.domain.repository.personal_quest;

import com.ssafy.fittapet.backend.common.constant.entity_field.QuestCategory;
import com.ssafy.fittapet.backend.domain.dto.quest.QuestResponse;
import com.ssafy.fittapet.backend.domain.entity.PersonalQuest;
import com.ssafy.fittapet.backend.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface PersonalQuestCustomRepository {
    List<QuestResponse> findByCategoryAndUser(QuestCategory category, User user);

    /**
     * 개인 퀘스트 id -> 퀘스트 함께 리턴
     */
    Optional<PersonalQuest> findByIdWithQuest(Long completeQuestId);
}
