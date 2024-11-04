package com.ssafy.fittapet.backend.application.service.quest;

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
        if(category == null || category.isEmpty()) return questRepository.findAllByQuestType("GUILD");
        else return questRepository.findAllByQuestTypeAndQuestCategory("GUILD", category);
    }
}
