package com.ssafy.fittapet.backend.domain.repository.pet;

import com.ssafy.fittapet.backend.common.constant.entity_field.PetStatus;
import com.ssafy.fittapet.backend.common.constant.entity_field.PetType;
import com.ssafy.fittapet.backend.domain.entity.Pet;
import com.ssafy.fittapet.backend.domain.entity.PetBook;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PetRepository extends JpaRepository<Pet, Long> {

    // 펫의 상태
    List<Pet> findByPetStatus(PetStatus petStatus);

    // 펫의 다음 진화 상태가 있는지 확인하고, 가져오기
    @Query("SELECT p FROM Pet p WHERE p.petType = :petType AND p.petStatus = :petStatus AND p.evolutionLevel = :evolutionLevel")
    Optional<Pet> findNextEvolution(PetType petType, PetStatus petStatus, Integer evolutionLevel);

    // 사용자가 소유하지 않은 '알' 상태의 펫 목록 조회
    @Query("SELECT p FROM Pet p WHERE p.id NOT IN :id AND p.petStatus = :petStatus")
    List<Pet> findByIdNotIn(List<Long> id, PetStatus petStatus);
}
