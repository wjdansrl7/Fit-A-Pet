package com.ssafy.fittapet.backend.common.config;

import com.ssafy.fittapet.backend.application.service.map.MapService;
import com.ssafy.fittapet.backend.common.constant.entity_field.*;
import com.ssafy.fittapet.backend.domain.entity.*;
import com.ssafy.fittapet.backend.domain.repository.auth.UserRepository;
import com.ssafy.fittapet.backend.domain.repository.guild.GuildRepository;
import com.ssafy.fittapet.backend.domain.repository.map.MapRepository;
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
    private final MapService mapService;
    private final MapRepository mapRepository;
    private final GuildRepository guildRepository;

    @PostConstruct
    void init() {


        if (questRepository.count() == 0) {
            /* 퀘스트 더미데이터 생성 */
            List<Quest> quests = new ArrayList<>();
            long reward = 100L;

            for (QuestTier questTier : QuestTier.values()) {
                for (QuestType questType : QuestType.values()) {
                    for (QuestCategory questCategory : QuestCategory.values()) {
                        String questName = questTier.getValue() + "_" + questType.getValue() + "_" + questCategory.getName();
                        String questContent = questType.getValue() + questCategory.getName();

                        Quest quest = createQuest(questName, questContent, questTier, questType, questCategory, reward);
                        quests.add(quest);
                    }
                }
            }
            questRepository.saveAll(quests);
        }

        if (userRepository.count() == 0) {
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

            if (personalQuestRepository.count() == 0) {
                /* 개인 퀘스트 더미데이터 생성 */
//                List<Quest> personalQuests = questRepository.findAllByQuestType(QuestType.PERSONAL);
//                User user = userRepository.findById(1L).orElse(null);
//
//                for(Quest quest : personalQuests) {
//                    PersonalQuest personalQuest = PersonalQuest.builder()
//                            .quest(quest)
//                            .user(user)
//                            .questStatus(quest.getId() % 3 != 0)
//                            .build();
//                    personalQuestRepository.save(personalQuest);
//                }
                addPersonalQuests(user1);
                addPersonalQuests(user2);
                addPersonalQuests(user3);
                addPersonalQuests(user4);
                addPersonalQuests(user5);
            }
        }

        if (guildRepository.count() == 0) {
            guildRepository.save(
                    Guild.builder().guildLeader(userRepository.findById(3L).orElse(null))
                            .guildName("Init Guild")
                            .build()
            );

            mapRepository.save(
                    Map.builder()
                            .guild(guildRepository.findByGuildName("Init Guild"))
                            .guildPosition(1L)
                            .user(userRepository.findById(3L).orElse(null))
                            .build()
            );

            mapRepository.save(
                    Map.builder()
                            .guild(guildRepository.findByGuildName("Init Guild"))
                            .guildPosition(1L)
                            .user(userRepository.findById(2L).orElse(null))
                            .build()
            );
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

    private void addPersonalQuests(User user) {

        List<Quest> quests = questRepository.findAll();
        List<PersonalQuest> personalQuests = quests.stream()
                .map(quest ->
                        PersonalQuest.builder()
                                .user(user)
                                .quest(quest)
                                .questStatus(false)
                                .build()
                )
                .toList();

        personalQuestRepository.saveAll(personalQuests);
    }
}
