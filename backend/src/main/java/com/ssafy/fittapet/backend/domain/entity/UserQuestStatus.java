package com.ssafy.fittapet.backend.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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
    @JoinColumn(name = "guild_quest_id")
    private GuildQuest guildQuest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public UserQuestStatus(Boolean questStatus, GuildQuest guildQuest, User user) {
        this.questStatus = questStatus;
        this.guildQuest = guildQuest;
        this.user = user;
    }

    /**
     * 상태 업데이트
     */
    public void updateStatus(boolean check){
        this.questStatus = check;
    }
}
