package com.ssafy.fittapet.backend.application.service.map;

import com.ssafy.fittapet.backend.domain.dto.map.MapResponse;

import java.util.List;

public interface MapService {
    List<MapResponse> getAll();
}
