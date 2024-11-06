package com.ssafy.fittapet.backend.application.service.quest;

import com.ssafy.fittapet.backend.common.constant.entity_field.QuestCategory;
import com.ssafy.fittapet.backend.common.constant.entity_field.QuestType;
import com.ssafy.fittapet.backend.common.exception.CustomException;
import com.ssafy.fittapet.backend.domain.entity.Quest;
import com.ssafy.fittapet.backend.domain.repository.quest.QuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ssafy.fittapet.backend.common.constant.error_code.QuestErrorCode.NOT_AVAILABLE_CATEGORY;

@Service
@RequiredArgsConstructor
public class QuestServiceImpl implements QuestService {
    private final QuestRepository questRepository;

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
}
