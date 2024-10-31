package com.ssafy.fittapet.backend.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonalQuest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "personal_quest_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Quest quest;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
