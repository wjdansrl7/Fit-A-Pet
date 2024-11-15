package com.ssafy.fittapet.backend.application.service.quest;

import com.ssafy.fittapet.backend.application.service.auth.AuthService;
import com.ssafy.fittapet.backend.application.service.petbook.PetBookService;
import com.ssafy.fittapet.backend.common.constant.entity_field.QuestCategory;
import com.ssafy.fittapet.backend.common.constant.entity_field.QuestType;
import com.ssafy.fittapet.backend.common.exception.CustomException;
import com.ssafy.fittapet.backend.domain.dto.quest.QuestCompleteRequestDTO;
import com.ssafy.fittapet.backend.domain.dto.quest.QuestQueryRequestDTO;
import com.ssafy.fittapet.backend.domain.dto.quest.QuestQueryResponseDTO;
import com.ssafy.fittapet.backend.domain.dto.quest.QuestResponse;
import com.ssafy.fittapet.backend.domain.entity.*;
import com.ssafy.fittapet.backend.domain.repository.auth.UserRepository;
import com.ssafy.fittapet.backend.domain.repository.personal_quest.PersonalQuestRepository;
import com.ssafy.fittapet.backend.domain.repository.quest.QuestRepository;
import com.ssafy.fittapet.backend.domain.repository.user_quest.UserQuestStatusRepository;
import jakarta.persistence.EntityNotFoundException;
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

    /**
     * 개인 퀘스트 완료
     * todo 경험치 Long, Integer / select 최소화 / 진화 여부 리턴
     */
    @Override
    public Long completePersonalQuest(QuestCompleteRequestDTO dto, Long userId) {
        PersonalQuest personalQuest = personalQuestRepository.findByIdWithQuest(dto.getCompleteQuestId())
                .orElseThrow(() -> new EntityNotFoundException("personalQuest not found"));

        // 퀘스트 상태 변경
        personalQuest.updateStatus(true);
        personalQuestRepository.save(personalQuest);

        // 퀘스트 보상
        Integer reward = Math.toIntExact(personalQuest.getQuest().getQuestReward());

        // 경험치 상승
        User user = authService.getLoginUser(userId);
        PetBook petBook = petBookService.selectPetBook(user.getPetMainId(), user);
        petBookService.updateExpAndEvolveCheck(petBook, reward);

        return personalQuest.getQuest().getQuestReward();
    }

    /**
     * 길드 퀘스트 완료
     * todo 경험치 Long, Integer / select 최소화 / 진화 여부 리턴
     */
    @Override
    public Long completeGuildQuest(QuestCompleteRequestDTO dto, Long userId) {

        UserQuestStatus userQuestStatus = userQuestStatusRepository.findByUserQuestStatusWithQuest(dto.getCompleteQuestId())
                .orElseThrow(() -> new EntityNotFoundException("userQuestStatus not found"));

        // 퀘스트 상태 변경
        userQuestStatus.updateStatus(true);
        userQuestStatusRepository.save(userQuestStatus);

        // 퀘스트 보상
        Integer reward = Math.toIntExact(userQuestStatus.getGuildQuest().getQuest().getQuestReward());

        // 경험치 상승
        User user = authService.getLoginUser(userId);
        PetBook petBook = petBookService.selectPetBook(user.getPetMainId(), user);
        petBookService.updateExpAndEvolveCheck(petBook, reward);

        return userQuestStatus.getGuildQuest().getQuest().getQuestReward();
    }

    /**
     * 카테고리 기반 쿼리 리턴
     */
    @Override
    public List<QuestQueryResponseDTO> queryQuest(QuestQueryRequestDTO dto) {
        return questRepository.findByCategory(dto.getQuestCategory());
    }
}
