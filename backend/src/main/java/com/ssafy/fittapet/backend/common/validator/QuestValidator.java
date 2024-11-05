package com.ssafy.fittapet.backend.common.validator;

import com.ssafy.fittapet.backend.domain.entity.Guild;
import com.ssafy.fittapet.backend.domain.entity.Quest;
import com.ssafy.fittapet.backend.domain.repository.quest.QuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class QuestValidator {
    private final QuestRepository questRepository;

    public Optional<Quest> isExist(Long questId){
        return questRepository.findById(questId);
    }
}
