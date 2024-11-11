package com.ssafy.fittapet.backend.common.config;

import com.ssafy.fittapet.backend.common.constant.entity_field.*;
import com.ssafy.fittapet.backend.domain.entity.PersonalQuest;
import com.ssafy.fittapet.backend.domain.entity.Quest;
import com.ssafy.fittapet.backend.domain.entity.User;
import com.ssafy.fittapet.backend.domain.repository.auth.UserRepository;
import com.ssafy.fittapet.backend.domain.repository.personal_quest.PersonalQuestRepository;
import com.ssafy.fittapet.backend.domain.repository.quest.QuestRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DbInit {

    private final UserRepository userRepository;
    private final QuestRepository questRepository;
    private final PersonalQuestRepository personalQuestRepository;

    @PostConstruct
    void init() {

        /* 유저 더미데이터 생성 */
        List<User> users = new ArrayList<>();

        var user1 = createUser("kakao 111", "김철수", "111", "kakao", "EASY", "USER");
        var user2 = createUser("kakao 222", "이영희", "222", "kakao", "NORMAL", "USER");
        var user3 = createUser("kakao 333", "박민수", "333", "kakao", "HARD", "USER");
        var user4 = createUser("kakao 444", "최영호", "444", "kakao", "EASY", "USER");
        var user5 = createUser("kakao 555", "장미희", "555", "kakao", "NORMAL", "USER");

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);

        userRepository.saveAll(users);

        /* 퀘스트 더미데이터 생성 */
        List<Quest> quests = new ArrayList<>();
        long reward = 100L;

        for(QuestTier questTier : QuestTier.values()) {
            for(QuestType questType : QuestType.values()) {
                for(QuestCategory questCategory : QuestCategory.values()) {
                    String questName = questTier.getValue() + "_" + questType.getValue() + "_" + questCategory.getName();
                    String questContent = questType.getValue()+questCategory.getName();

                    Quest quest = createQuest(questName, questContent, questTier, questType, questCategory, reward);
                    quests.add(quest);
                }
            }
        }
        questRepository.saveAll(quests);

        /* 개인 퀘스트 더미데이터 생성 */
        List<Quest> personalQuests = questRepository.findAllByQuestType(QuestType.PERSONAL);
        User user = userRepository.findById(1L).orElse(null);

        for(Quest quest : personalQuests) {
            PersonalQuest personalQuest = PersonalQuest.builder()
                    .quest(quest)
                    .user(user)
                    .questStatus(false)
                    .build();
            personalQuestRepository.save(personalQuest);
        }
    }

    private User createUser(String userUniqueName, String userName, String providerId, String provider, String
            userTier, String role) {
        return User.builder()
                .userUniqueName(userUniqueName)
                .userName(userName)
                .providerId(providerId)
                .provider(provider)
                .userTier(UserTier.valueOf(userTier))
                .role(Role.valueOf(role))
                .build();
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
