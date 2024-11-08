package com.ssafy.fittapet.backend.application.service.quest;

import com.ssafy.fittapet.backend.common.constant.entity_field.QuestCategory;
import com.ssafy.fittapet.backend.common.constant.entity_field.QuestType;
import com.ssafy.fittapet.backend.common.exception.CustomException;
import com.ssafy.fittapet.backend.domain.dto.quest.QuestResponse;
import com.ssafy.fittapet.backend.domain.entity.Quest;
import com.ssafy.fittapet.backend.domain.entity.User;
import com.ssafy.fittapet.backend.domain.repository.auth.UserRepository;
import com.ssafy.fittapet.backend.domain.repository.personal_quest.PersonalQuestRepository;
import com.ssafy.fittapet.backend.domain.repository.quest.QuestRepository;
import com.ssafy.fittapet.backend.domain.repository.user_quest.UserQuestStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ssafy.fittapet.backend.common.constant.error_code.QuestErrorCode.NOT_AVAILABLE_CATEGORY;

@Service
@RequiredArgsConstructor
public class QuestServiceImpl implements QuestService {
    private final QuestRepository questRepository;
    private final PersonalQuestRepository personalQuestRepository;
    private final UserRepository userRepository;
    private final UserQuestStatusRepository userQuestStatusRepository;

    @Override
    public List<Quest> searchGuildQuest(String category) throws CustomException {
        if(category == null || category.isEmpty()) return questRepository.findAllByQuestType(QuestType.GROUP);

        try{
            QuestCategory questCategory = QuestCategory.valueOf(category.toUpperCase());
            return questRepository.findAllByQuestTypeAndQuestCategory(QuestType.GROUP, questCategory);
        } catch (IllegalArgumentException e) {
            throw new CustomException(NOT_AVAILABLE_CATEGORY);
        }
    }

    @Override
    public Map<String, List<QuestResponse>> getMyQuestList() {
        // todo : 요청자 찾기
        User user = userRepository.findById(1L).orElse(null);

        Map<String, List<QuestResponse>> response = new HashMap<>();

        response.put("personalWalk", personalQuestRepository.findByCategoryAndUser(QuestCategory.WALK, user));
        response.put("personalSleep", personalQuestRepository.findByCategoryAndUser(QuestCategory.SLEEP, user));
        response.put("personalDiet", personalQuestRepository.findByCategoryAndUser(QuestCategory.DIET, user));

        response.put("guildQuest", userQuestStatusRepository.findByUser(user));

        return response;
    }
}
