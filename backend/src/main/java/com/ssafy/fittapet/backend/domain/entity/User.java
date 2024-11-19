package com.ssafy.fittapet.backend.domain.entity;

import com.ssafy.fittapet.backend.common.constant.entity_field.Role;
import com.ssafy.fittapet.backend.common.constant.entity_field.UserTier;
import jakarta.persistence.*;
import lombok.*;

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

    private String userName;
    private String userUniqueName;
    private String provider;
    private String providerId;

    @Enumerated(EnumType.STRING)
    private UserTier userTier;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Long petMainId;

    public void updatePetMainId(Long petMainId) {
        this.petMainId = petMainId;
    }
}
