package com.ssafy.fittapet.backend.domain.repository.quest;

import com.ssafy.fittapet.backend.domain.entity.Quest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestRepository extends JpaRepository<Quest, Long> {
    List<Quest> findAllByQuestType(String questType);

    List<Quest> findAllByQuestTypeAndQuestCategory(String questType, String questCategory);
}
