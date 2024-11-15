package com.ssafy.fittapet.backend.domain.repository.personal_quest;

import com.ssafy.fittapet.backend.common.constant.entity_field.QuestCategory;
import com.ssafy.fittapet.backend.domain.dto.quest.QuestResponse;
import com.ssafy.fittapet.backend.domain.entity.PersonalQuest;
import com.ssafy.fittapet.backend.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface PersonalQuestCustomRepository {

    List<QuestResponse> findByCategoryAndUser(QuestCategory category, User user);

    Optional<PersonalQuest> findByUserAndQuest(Long userId, Long questId);
}
