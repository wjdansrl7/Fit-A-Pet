package com.ssafy.fittapet.backend.domain.repository.pet;

import com.ssafy.fittapet.backend.common.constant.entity_field.PetStatus;
import com.ssafy.fittapet.backend.domain.entity.Pet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PetRepository extends JpaRepository<Pet, Long> {

    @Query("SELECT p FROM Pet p WHERE p.id NOT IN :id AND p.petStatus = :petStatus")
    List<Pet> findByIdNotInAndPetStatus(List<Long> id, PetStatus petStatus);
}
