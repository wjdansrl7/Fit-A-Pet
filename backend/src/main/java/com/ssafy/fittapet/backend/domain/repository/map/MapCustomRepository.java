package com.ssafy.fittapet.backend.domain.repository.map;

import com.ssafy.fittapet.backend.domain.dto.map.MapResponse;

import java.util.List;

public interface MapCustomRepository {
    List<MapResponse> findAllByUserId(Long userId);
}
