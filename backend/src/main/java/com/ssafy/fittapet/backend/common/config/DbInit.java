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

            quests.add(createQuest("즐거운 산책", "5000걸음 달성하기", QuestTier.EASY, QuestType.PERSONAL, QuestCategory.WALK, 100));
            quests.add(createQuest("좀 더 멀리", "10000걸음 달성하기", QuestTier.NORMAL, QuestType.PERSONAL, QuestCategory.WALK, 100));
            quests.add(createQuest("걸어서 세계속으로", "15000걸음 달성하기", QuestTier.HARD, QuestType.PERSONAL, QuestCategory.WALK, 100));
            quests.add(createQuest("개운한 시작", "7시간 수면 달성", QuestTier.EASY, QuestType.PERSONAL, QuestCategory.SLEEP, 100));
            quests.add(createQuest("깊은 숙면", "8시간 수면 달성", QuestTier.NORMAL, QuestType.PERSONAL, QuestCategory.SLEEP, 100));
            quests.add(createQuest("살아계신가요?", "9시간 수면 달성", QuestTier.HARD, QuestType.PERSONAL, QuestCategory.SLEEP, 100));
            quests.add(createQuest("밥은 먹고 하자", "1권장 영양량 달성하기", QuestTier.EASY, QuestType.PERSONAL, QuestCategory.DIET, 100));
            quests.add(createQuest("잘 먹는 사람", "2권장 영양량 달성하기", QuestTier.NORMAL, QuestType.PERSONAL, QuestCategory.DIET, 100));
            quests.add(createQuest("식단 관리의 신", "3권장 영양량 달성하기", QuestTier.HARD, QuestType.PERSONAL, QuestCategory.DIET, 100));

            quests.add(createQuest("길드 걸음 1단계", "5000걸음 달성하기", QuestTier.EASY, QuestType.GROUP, QuestCategory.WALK, 100));
            quests.add(createQuest("길드 걸음 2단계", "10000걸음 달성하기", QuestTier.NORMAL, QuestType.GROUP, QuestCategory.WALK, 100));
            quests.add(createQuest("길드 걸음 3단계", "15000걸음 달성하기", QuestTier.HARD, QuestType.GROUP, QuestCategory.WALK, 100));
            quests.add(createQuest("길드 수면 1단계", "7시간 수면 달성", QuestTier.EASY, QuestType.GROUP, QuestCategory.SLEEP, 100));
            quests.add(createQuest("길드 수면 2단계", "8시간 수면 달성", QuestTier.NORMAL, QuestType.GROUP, QuestCategory.SLEEP, 100));
            quests.add(createQuest("길드 수면 3단계", "9시간 수면 달성", QuestTier.HARD, QuestType.GROUP, QuestCategory.SLEEP, 100));
            quests.add(createQuest("길드 식단 1단계", "1권장 영양량 달성하기", QuestTier.EASY, QuestType.GROUP, QuestCategory.DIET, 100));
            quests.add(createQuest("길드 식단 2단계", "2권장 영양량 달성하기", QuestTier.NORMAL, QuestType.GROUP, QuestCategory.DIET, 100));
            quests.add(createQuest("길드 식단 3단계", "3권장 영양량 달성하기", QuestTier.HARD, QuestType.GROUP, QuestCategory.DIET, 100));

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
