package com.ssafy.fittapet.backend.domain.dto.quest;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.fittapet.backend.domain.entity.Quest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class QuestResponse {
    Long id;
    String questName;
    String questContent;
    String questTier;
    String questCategory;
    boolean questStatus;
    Long questReward;

    @Builder
    @QueryProjection
    public QuestResponse(Long id, Quest quest, Boolean questStatus) {
        this.id = id;
        this.questName = quest.getQuestName();
        this.questContent = quest.getQuestContent();
        this.questTier = quest.getQuestTier().getValue();
        this.questCategory = quest.getQuestCategory().getName();
        this.questStatus = questStatus;
        this.questReward = quest.getQuestReward();
    }
}
