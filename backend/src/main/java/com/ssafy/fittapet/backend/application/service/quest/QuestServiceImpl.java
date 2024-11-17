package com.ssafy.fittapet.backend.application.service.quest;

import com.ssafy.fittapet.backend.application.service.auth.AuthService;
import com.ssafy.fittapet.backend.application.service.petbook.PetBookService;
import com.ssafy.fittapet.backend.common.constant.entity_field.QuestCategory;
import com.ssafy.fittapet.backend.common.constant.entity_field.QuestType;
import com.ssafy.fittapet.backend.common.exception.CustomException;
import com.ssafy.fittapet.backend.domain.dto.quest.QuestCompleteRequest;
import com.ssafy.fittapet.backend.domain.dto.quest.QuestResponse;
import com.ssafy.fittapet.backend.domain.entity.*;
import com.ssafy.fittapet.backend.domain.repository.auth.UserRepository;
import com.ssafy.fittapet.backend.domain.repository.personal_quest.PersonalQuestRepository;
import com.ssafy.fittapet.backend.domain.repository.quest.QuestRepository;
import com.ssafy.fittapet.backend.domain.repository.user_quest.UserQuestStatusRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ssafy.fittapet.backend.common.constant.error_code.QuestErrorCode.NOT_AVAILABLE_CATEGORY;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestServiceImpl implements QuestService {

    private final QuestRepository questRepository;
    private final PersonalQuestRepository personalQuestRepository;
    private final UserRepository userRepository;
    private final UserQuestStatusRepository userQuestStatusRepository;
    private final PetBookService petBookService;
    private final AuthService authService;

    @Override
    public List<Quest> searchGuildQuest(String category) throws CustomException {
        if (category == null || category.isEmpty()) return questRepository.findAllByQuestType(QuestType.GROUP);

        try {
            QuestCategory questCategory = QuestCategory.valueOf(category.toUpperCase());
            return questRepository.findAllByQuestTypeAndQuestCategory(QuestType.GROUP, questCategory);
        } catch (IllegalArgumentException e) {
            throw new CustomException(NOT_AVAILABLE_CATEGORY);
        }
    }

    @Override
    public Map<String, List<QuestResponse>> getMyQuestList(Long userId) {
        // todo : 요청자 찾기
        User user = userRepository.findById(userId).orElse(null);

        Map<String, List<QuestResponse>> response = new HashMap<>();

        response.put("personalWalk", personalQuestRepository.findByCategoryAndUser(QuestCategory.WALK, user));
        response.put("personalSleep", personalQuestRepository.findByCategoryAndUser(QuestCategory.SLEEP, user));
        response.put("personalDiet", personalQuestRepository.findByCategoryAndUser(QuestCategory.DIET, user));

        response.put("guildQuest", userQuestStatusRepository.findByUser(user));

        return response;
    }

    @Override
    public Long completePersonalQuest(QuestCompleteRequest dto, Long userId) {

        PersonalQuest personalQuest = personalQuestRepository.findByUserAndQuest(userId, dto.getCompleteQuestId())
                .orElseThrow(() -> new EntityNotFoundException("personalQuest not found"));

        if (personalQuest.isQuestStatus()) {
            return 0L;
        }

        personalQuest.updateStatus(true);
        personalQuestRepository.save(personalQuest);

        Integer reward = Math.toIntExact(personalQuest.getQuest().getQuestReward());

        User user = personalQuest.getUser();
        PetBook petBook = petBookService.selectPetBook(user.getPetMainId(), user);
        petBookService.updateExpAndEvolveCheck(petBook, reward);

        return personalQuest.getQuest().getQuestReward();
    }

    @Override
    public Long completeGuildQuest(QuestCompleteRequest dto, Long userId) {

        UserQuestStatus userQuestStatus = userQuestStatusRepository.findByUserQuestStatusWithQuest(dto.getCompleteQuestId())
                .orElseThrow(() -> new EntityNotFoundException("userQuestStatus not found"));

        userQuestStatus.updateStatus(true);
        userQuestStatusRepository.save(userQuestStatus);

        Integer reward = Math.toIntExact(userQuestStatus.getGuildQuest().getQuest().getQuestReward());

        User user = authService.getLoginUser(userId);
        PetBook petBook = petBookService.selectPetBook(user.getPetMainId(), user);
        petBookService.updateExpAndEvolveCheck(petBook, reward);

        return userQuestStatus.getGuildQuest().getQuest().getQuestReward();
    }
}
