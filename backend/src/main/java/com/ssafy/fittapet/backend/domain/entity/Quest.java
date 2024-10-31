package com.ssafy.fittapet.backend.domain.entity;

import com.ssafy.fittapet.backend.common.constant.QuestCategory;
import com.ssafy.fittapet.backend.common.constant.QuestTier;
import com.ssafy.fittapet.backend.common.constant.QuestType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quest {

    @Id
    @Column(name = "quest_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String questContent;

    @Enumerated(EnumType.STRING)
    private QuestTier questTier;

    private long questReward;
    private String questName;

    @Enumerated(EnumType.STRING)
    private QuestType questType;

    @Enumerated(EnumType.STRING)
    private QuestCategory questCategory;

}
