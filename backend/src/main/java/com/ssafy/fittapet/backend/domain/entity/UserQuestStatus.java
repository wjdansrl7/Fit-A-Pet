package com.ssafy.fittapet.backend.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserQuestStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quest_status_id")
    private long id;

    @Column
    private Boolean questStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_quest_id")
    private GroupQuest groupQuest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public UserQuestStatus(Boolean questStatus, GroupQuest groupQuest, User user) {
        this.questStatus = questStatus;
        this.groupQuest = groupQuest;
    }
}
