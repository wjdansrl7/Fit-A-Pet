package com.ssafy.fittapet.backend.application.service.map;

import com.ssafy.fittapet.backend.domain.dto.map.MapResponse;
import com.ssafy.fittapet.backend.domain.repository.Map.MapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MapServiceImpl implements MapService {
    private final MapRepository mapRepository;

    @Override
    public List<MapResponse> getAll() {
        // todo : 로그인한 유저 id 갖고오기
        Long userId = 1L;

        return mapRepository.findAllByUserId(userId);
    }
}
