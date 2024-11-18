package com.ssafy.fittapet.backend.domain.dto.quest;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.fittapet.backend.domain.entity.Quest;
import lombok.Builder;
import lombok.Getter;

@Getter
public class QuestQueryResponseDTO {

    Long questId;
    String questName;
    String questContent;
    String questTier;
    String questCategory;

    @QueryProjection
    @Builder
    public QuestQueryResponseDTO(Quest quest) {
        this.questId = quest.getId();
        this.questName = quest.getQuestName();
        this.questContent = quest.getQuestContent();
        this.questTier = quest.getQuestTier().getValue();
        this.questCategory = quest.getQuestCategory().getName();
    }
}
