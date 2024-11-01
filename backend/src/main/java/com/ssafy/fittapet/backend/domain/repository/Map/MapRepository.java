package com.ssafy.fittapet.backend.domain.repository.Map;

import com.ssafy.fittapet.backend.domain.entity.Map;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MapRepository extends JpaRepository<Map, Long>, MapCustomRepository {
}
