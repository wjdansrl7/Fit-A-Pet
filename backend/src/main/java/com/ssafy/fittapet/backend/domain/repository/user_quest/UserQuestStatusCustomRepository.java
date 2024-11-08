package com.ssafy.fittapet.backend.domain.repository.user_quest;

import com.ssafy.fittapet.backend.domain.dto.quest.QuestResponse;
import com.ssafy.fittapet.backend.domain.entity.User;

import java.util.List;

public interface UserQuestStatusCustomRepository {
    List<QuestResponse> findByUser(User user);
}
