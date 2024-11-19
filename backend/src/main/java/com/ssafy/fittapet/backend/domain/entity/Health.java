package com.ssafy.fittapet.backend.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Health extends BaseEntity {

    @Builder
    public Health(User user, Integer stepCnt, Integer sleepTime) {
        this.user = user;
        this.stepCnt = stepCnt;
        this.sleepTime = sleepTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "health_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Integer stepCnt = 0;

    private Integer sleepTime = 0;

    public void updateStepCnt(Integer stepCnt) {
        this.stepCnt += stepCnt;
    }

    public void updateSleepTime(Integer sleepTime) {
        this.sleepTime += sleepTime;
    }


}
