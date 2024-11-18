package com.ssafy.fittapet.backend.application.service.quest;

import com.ssafy.fittapet.backend.domain.entity.PersonalQuest;
import com.ssafy.fittapet.backend.domain.repository.personal_quest.PersonalQuestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class QuestScheduler {

    private final PersonalQuestRepository personalQuestRepository;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void reportCurrentTime() {

        // 1. 모든 PersonalQuest
        List<PersonalQuest> personalQuests = personalQuestRepository.findAll();

        // 2. 각 PersonalQuest questStatus false
        personalQuests.forEach(personalQuest -> personalQuest.updateStatus(false));

        // 3. 변경된 PersonalQuest 저장
        personalQuestRepository.saveAll(personalQuests);
    }
}
