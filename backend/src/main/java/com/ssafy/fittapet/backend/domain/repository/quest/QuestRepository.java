package com.ssafy.fittapet.backend.domain.repository.quest;

import com.ssafy.fittapet.backend.common.constant.entity_field.QuestCategory;
import com.ssafy.fittapet.backend.common.constant.entity_field.QuestType;
import com.ssafy.fittapet.backend.domain.entity.Quest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestRepository extends JpaRepository<Quest, Long> {
    List<Quest> findAllByQuestType(QuestType questType);

    List<Quest> findAllByQuestTypeAndQuestCategory(QuestType questType, QuestCategory questCategory);
}
