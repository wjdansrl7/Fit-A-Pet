package com.ssafy.fittapet.backend.application.service.quest;

import com.ssafy.fittapet.backend.domain.entity.PersonalQuest;
import com.ssafy.fittapet.backend.domain.repository.personal_quest.PersonalQuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QuestScheduler {

    private final PersonalQuestRepository personalQuestRepository;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void reportCurrentTime() {

        List<PersonalQuest> personalQuests = personalQuestRepository.findAll();

        personalQuests.forEach(personalQuest -> personalQuest.updateStatus(false));

        personalQuestRepository.saveAll(personalQuests);
    }
}
