package com.ssafy.fittapet.backend.application.service.quest;

import com.ssafy.fittapet.backend.application.service.auth.AuthService;
import com.ssafy.fittapet.backend.application.service.petbook.PetBookService;
import com.ssafy.fittapet.backend.common.constant.entity_field.QuestCategory;
import com.ssafy.fittapet.backend.common.constant.entity_field.QuestType;
import com.ssafy.fittapet.backend.common.exception.CustomException;
import com.ssafy.fittapet.backend.domain.dto.quest.QuestCompleteRequest;
import com.ssafy.fittapet.backend.domain.dto.map.MapGuildQuestDTO;
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
    public Map<String, Object> completePersonalQuest(QuestCompleteRequest dto, Long userId) {

        PersonalQuest personalQuest = personalQuestRepository.findByUserAndQuest(userId, dto.getCompleteQuestId())
                .orElseThrow(() -> new EntityNotFoundException("personalQuest not found"));

        personalQuest.updateStatus(true);
        personalQuestRepository.save(personalQuest);

        Integer reward = Math.toIntExact(personalQuest.getQuest().getQuestReward());

        User user = personalQuest.getUser();
        PetBook petBook = petBookService.findPetBookById(user.getPetMainId(), user);

        Map<String, Object> response = new HashMap<>();
        response.put("shouldShowModal", petBookService.processQuestCompletion(petBook, reward, user));
        response.put("petType", petBook.getPet().getPetType());
        response.put("petStatus", petBook.getPet().getPetStatus());

        // 새로운 알 생성 가능 여부 반환
        return response;

    }

    @Override
    public Map<String, Object> completeGuildQuest(QuestCompleteRequest dto, Long userId) throws CustomException {

//        UserQuestStatus userQuestStatus = userQuestStatusRepository.findByUserQuestStatusWithQuest(dto.getCompleteQuestId())
//                .orElseThrow(() -> new EntityNotFoundException("userQuestStatus not found"));

        Quest quest = questRepository.findById(dto.getCompleteQuestId()).orElseThrow(()-> new CustomException(NO_QUEST));

        // 필요 로직 :: 해당 퀘스트가 이용자가 진행중인 길드퀘스트인지 확인하기.
        // Map, GuildQuest, UserQuestStatus 테이블을 조인 후 user와 quest로 해당하는 값들을 찾음
        // list로 받은 것은 같은 퀘스트를 갖고 있는 길드들에 가입했을 경우를 위해.
        // list 안의 userQuestStatus ID를 이용해 기존의 로직 수행.
        List<MapGuildQuestDTO> list = mapRepository.findAllMGQByUserId(userId, quest);
        Map<String, Object> response = new HashMap<>();
        for(MapGuildQuestDTO dtoMap : list) {
            // 퀘스트 상태 변경
            UserQuestStatus userQuestStatus = userQuestStatusRepository.findById(dtoMap.getUserQuestStatusId()).orElse(null);
            userQuestStatus.updateStatus(true);
            userQuestStatusRepository.save(userQuestStatus);

            // 퀘스트 보상
            Integer reward = Math.toIntExact(userQuestStatus.getGuildQuest().getQuest().getQuestReward());

            // 경험치 상승
            User user = authService.getLoginUser(userId);
            PetBook petBook = petBookService.findPetBookById(user.getPetMainId(), user);

            response.put("shouldShowModal", petBookService.processQuestCompletion(petBook, reward, user));
            response.put("petType", petBook.getPet().getPetType());
            response.put("petStatus", petBook.getPet().getPetStatus());
        }

        return response;

//        // 퀘스트 상태 변경
//        userQuestStatus.updateStatus(true);
//        userQuestStatusRepository.save(userQuestStatus);
//
//        // 퀘스트 보상
//        Integer reward = Math.toIntExact(userQuestStatus.getGuildQuest().getQuest().getQuestReward());
//
//        // 경험치 상승
//        User user = authService.getLoginUser(userId);
//        PetBook petBook = petBookService.selectPetBook(user.getPetMainId(), user);
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("shouldShowModal", petBookService.updateExpAndEvolveCheck(petBook, reward, user));
//        response.put("petType", petBook.getPet().getPetType());
//        response.put("petStatus", petBook.getPet().getPetStatus());
//
//        return response;

    }
}
