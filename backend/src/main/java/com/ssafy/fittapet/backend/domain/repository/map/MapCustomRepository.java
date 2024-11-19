package com.ssafy.fittapet.backend.domain.repository.map;

import com.ssafy.fittapet.backend.domain.dto.map.MapGuildQuestDTO;
import com.ssafy.fittapet.backend.domain.dto.map.MapResponse;
import com.ssafy.fittapet.backend.domain.entity.Quest;

import java.util.List;

public interface MapCustomRepository {

    List<MapResponse> findAllByUserId(Long userId);

    List<MapGuildQuestDTO> findAllMGQByUserId(Long userId, Quest quest);
}
