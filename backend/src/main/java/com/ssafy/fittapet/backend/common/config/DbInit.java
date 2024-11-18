package com.ssafy.fittapet.backend.common.config;

import com.ssafy.fittapet.backend.common.constant.entity_field.*;
import com.ssafy.fittapet.backend.domain.entity.*;
import com.ssafy.fittapet.backend.domain.repository.quest.QuestRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DbInit {

    private final QuestRepository questRepository;

    @PostConstruct
    void init() {

        if (questRepository.count() == 0) {

            List<Quest> quests = new ArrayList<>();

            quests.add(createQuest("즐거운 산책", "5000걸음", QuestTier.EASY, QuestType.PERSONAL, QuestCategory.WALK, 0));
            quests.add(createQuest("좀 더 멀리", "10000걸음", QuestTier.NORMAL, QuestType.PERSONAL, QuestCategory.WALK, 0));
            quests.add(createQuest("걸어서 세계속으로", "15000걸음", QuestTier.HARD, QuestType.PERSONAL, QuestCategory.WALK, 0));
            quests.add(createQuest("개운한 시작", "7시간", QuestTier.EASY, QuestType.PERSONAL, QuestCategory.SLEEP, 5000));
            quests.add(createQuest("깊은 숙면", "8시간", QuestTier.NORMAL, QuestType.PERSONAL, QuestCategory.SLEEP, 0));
            quests.add(createQuest("살아계신가요?", "9시간", QuestTier.HARD, QuestType.PERSONAL, QuestCategory.SLEEP, 0));
            quests.add(createQuest("밥은 먹고 하자", "탄단지1", QuestTier.EASY, QuestType.PERSONAL, QuestCategory.DIET, 0));
            quests.add(createQuest("잘 먹는 사람", "탄단지2", QuestTier.NORMAL, QuestType.PERSONAL, QuestCategory.DIET, 0));
            quests.add(createQuest("식단 관리의 신", "탄단지3", QuestTier.HARD, QuestType.PERSONAL, QuestCategory.DIET, 0));

            quests.add(createQuest("즐거운 산책", "5000걸음", QuestTier.EASY, QuestType.GROUP, QuestCategory.WALK, 0));
            quests.add(createQuest("좀 더 멀리", "10000걸음", QuestTier.NORMAL, QuestType.GROUP, QuestCategory.WALK, 0));
            quests.add(createQuest("걸어서 세계속으로", "15000걸음", QuestTier.HARD, QuestType.GROUP, QuestCategory.WALK, 0));
            quests.add(createQuest("개운한 시작", "7시간", QuestTier.EASY, QuestType.GROUP, QuestCategory.SLEEP, 0));
            quests.add(createQuest("깊은 숙면", "8시간", QuestTier.NORMAL, QuestType.GROUP, QuestCategory.SLEEP, 0));
            quests.add(createQuest("살아계신가요?", "9시간", QuestTier.HARD, QuestType.GROUP, QuestCategory.SLEEP, 0));
            quests.add(createQuest("밥은 먹고 하자", "탄단지1", QuestTier.EASY, QuestType.GROUP, QuestCategory.DIET, 0));
            quests.add(createQuest("잘 먹는 사람", "탄단지2", QuestTier.NORMAL, QuestType.GROUP, QuestCategory.DIET, 0));
            quests.add(createQuest("식단 관리의 신", "탄단지3", QuestTier.HARD, QuestType.GROUP, QuestCategory.DIET, 0));

            questRepository.saveAll(quests);
        }
    }

    private Quest createQuest(String questName, String questContent, QuestTier questTier, QuestType questType, QuestCategory questCategory, long questReward) {
        return Quest.builder()
                .questName(questName)
                .questContent(questContent)
                .questTier(questTier)
                .questType(questType)
                .questCategory(questCategory)
                .questReward(questReward)
                .build();
    }
}
