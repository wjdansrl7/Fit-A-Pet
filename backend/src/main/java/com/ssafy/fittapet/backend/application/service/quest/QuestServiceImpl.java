package com.ssafy.fittapet.backend.application.service.quest;

import com.ssafy.fittapet.backend.common.constant.QuestCategory;
import com.ssafy.fittapet.backend.common.constant.QuestType;
import com.ssafy.fittapet.backend.domain.entity.Quest;
import com.ssafy.fittapet.backend.domain.repository.quest.QuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestServiceImpl implements QuestService {
    private final QuestRepository questRepository;

    @Override
    public List<Quest> searchGuildQuest(String category) {
        if(category == null || category.isEmpty()) return questRepository.findAllByQuestType(QuestType.GROUP);
        else return questRepository.findAllByQuestTypeAndQuestCategory(QuestType.GROUP, QuestCategory.valueOf(category.toUpperCase()));
    }
}
