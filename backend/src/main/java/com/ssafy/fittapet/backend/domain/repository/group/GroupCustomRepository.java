package com.ssafy.fittapet.backend.domain.repository.group;

import com.ssafy.fittapet.backend.domain.dto.map.MapResponse;

import java.util.List;

public interface GroupCustomRepository {
    List<MapResponse> findAllByUserId(Long userId);
}
