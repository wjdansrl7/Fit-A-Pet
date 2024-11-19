package com.ssafy.fittapet.backend.domain.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;

@Getter
@Embeddable
public class Nutrient implements Serializable {

    private double serving;
    private Integer ratio;

    public Nutrient() {
    }

    @Builder
    public Nutrient(double serving, Integer ratio) {
        this.serving = serving;
        this.ratio = ratio;
    }
}
