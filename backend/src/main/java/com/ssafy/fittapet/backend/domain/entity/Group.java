package com.ssafy.fittapet.backend.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Group extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long id;

    @Column
    private String groupName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User groupLeader;

    @Builder
    public Group(String groupName, User groupLeader) {
        this.groupName = groupName;
        this.groupLeader = groupLeader;
    }
}
