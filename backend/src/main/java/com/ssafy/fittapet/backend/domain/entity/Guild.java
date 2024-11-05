package com.ssafy.fittapet.backend.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Guild extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guild_id")
    private Long id;

    @Column
    private String guildName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User guildLeader;

    @Builder
    public Guild(String guildName, User guildLeader) {
        this.guildName = guildName;
        this.guildLeader = guildLeader;
    }
}
