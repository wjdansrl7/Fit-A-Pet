package com.ssafy.fittapet.backend.application.service.quest;

import com.ssafy.fittapet.backend.application.service.auth.AuthService;
import com.ssafy.fittapet.backend.application.service.petbook.PetBookService;
import com.ssafy.fittapet.backend.common.constant.entity_field.QuestCategory;
import com.ssafy.fittapet.backend.common.constant.entity_field.QuestType;
import com.ssafy.fittapet.backend.common.exception.CustomException;
import com.ssafy.fittapet.backend.domain.dto.map.MapGuildQuestDTO;
import com.ssafy.fittapet.backend.domain.dto.quest.QuestCompleteRequestDTO;
import com.ssafy.fittapet.backend.domain.dto.quest.QuestQueryRequestDTO;
import com.ssafy.fittapet.backend.domain.dto.quest.QuestQueryResponseDTO;
import com.ssafy.fittapet.backend.domain.dto.quest.QuestResponse;
import com.ssafy.fittapet.backend.domain.entity.*;
import com.ssafy.fittapet.backend.domain.repository.auth.UserRepository;
import com.ssafy.fittapet.backend.domain.repository.map.MapRepository;
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
import static com.ssafy.fittapet.backend.common.constant.error_code.QuestErrorCode.NO_QUEST;

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
    private final MapRepository mapRepository;

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
        User user = userRepository.findById(userId).orElse(null);

        Map<String, List<QuestResponse>> response = new HashMap<>();

        response.put("personalWalk", personalQuestRepository.findByCategoryAndUser(QuestCategory.WALK, user));
        response.put("personalSleep", personalQuestRepository.findByCategoryAndUser(QuestCategory.SLEEP, user));
        response.put("personalDiet", personalQuestRepository.findByCategoryAndUser(QuestCategory.DIET, user));

        response.put("guildQuest", userQuestStatusRepository.findByUser(user));

        return response;
    }

    @Override
    public Map<String, Object> completePersonalQuest(QuestCompleteRequestDTO dto, Long userId) {

        PersonalQuest personalQuest = personalQuestRepository.findByUserAndQuest(userId, dto.getCompleteQuestId())
                .orElseThrow(() -> new EntityNotFoundException("personalQuest not found"));

        Map<String, Object> response = new HashMap<>();

        if (personalQuest.isQuestStatus()) {
            return response;
        }

        personalQuest.updateStatus(true);
        personalQuestRepository.save(personalQuest);

        Integer reward = Math.toIntExact(personalQuest.getQuest().getQuestReward());

        User user = personalQuest.getUser();
        PetBook petBook = petBookService.findPetBookById(user.getPetMainId(), user);

        response.put("shouldShowModal", petBookService.processQuestCompletion(petBook, reward, user));
        PetBook completedAfterPetBook = petBookService.findPetBookById(user.getPetMainId(), user);
        response.put("petType", completedAfterPetBook.getPet().getPetType().getValue());
        response.put("petStatus", completedAfterPetBook.getPet().getPetStatus().getValue());

        return response;
    }

    @Override
    public Map<String, Object> completeGuildQuest(QuestCompleteRequestDTO dto, Long userId) throws CustomException {

        Quest quest = questRepository.findById(dto.getCompleteQuestId()).orElseThrow(() -> new CustomException(NO_QUEST));

        List<MapGuildQuestDTO> list = mapRepository.findAllMGQByUserId(userId, quest);
        Map<String, Object> response = new HashMap<>();
        for (MapGuildQuestDTO dtoMap : list) {
            UserQuestStatus userQuestStatus = userQuestStatusRepository.findById(dtoMap.getUserQuestStatusId()).orElse(null);
            assert userQuestStatus != null;
            userQuestStatus.updateStatus(true);
            userQuestStatusRepository.save(userQuestStatus);

            Integer reward = Math.toIntExact(userQuestStatus.getGuildQuest().getQuest().getQuestReward());

            User user = authService.getLoginUser(userId);
            PetBook petBook = petBookService.findPetBookById(user.getPetMainId(), user);

            response.put("shouldShowModal", petBookService.processQuestCompletion(petBook, reward, user));
            PetBook completedPetBook = petBookService.findPetBookById(user.getPetMainId(), user);

            response.put("petType", completedPetBook.getPet().getPetType().getValue());
            response.put("petStatus", completedPetBook.getPet().getPetStatus().getValue());
        }
        return response;
    }

    @Override
    public List<QuestQueryResponseDTO> queryQuest(QuestQueryRequestDTO dto) {
        return questRepository.findByCategory(dto.getQuestCategory());
    }
}
