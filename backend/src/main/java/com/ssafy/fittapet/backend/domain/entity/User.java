package com.ssafy.fittapet.backend.domain.entity;

import com.ssafy.fittapet.backend.common.constant.Role;
import com.ssafy.fittapet.backend.common.constant.UserTier;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userNickname;

    private String userName;

    private String providerId;

    private String provider;

    @Enumerated(EnumType.STRING)
    private UserTier userTier;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<PersonalQuest> personalQuests = new ArrayList<>();

}
