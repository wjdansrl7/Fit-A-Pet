package com.ssafy.fittapet.backend.domain.repository.user_quest;

import com.ssafy.fittapet.backend.domain.dto.quest.QuestResponse;
import com.ssafy.fittapet.backend.domain.entity.User;
import com.ssafy.fittapet.backend.domain.entity.UserQuestStatus;

import java.util.List;
import java.util.Optional;

public interface UserQuestStatusCustomRepository {
    List<QuestResponse> findByUser(User user);

    /**
     * 유저 퀘스트 상태 id -> 길드 퀘스트, 퀘스트 함께 리턴
     */
    Optional<UserQuestStatus> findByUserQuestStatusWithQuest(Long completeQuestId);
}
