package com.ssafy.fittapet.backend.application.service.pet;

import com.ssafy.fittapet.backend.common.constant.entity_field.PetStatus;
import com.ssafy.fittapet.backend.domain.entity.Pet;

import java.util.List;
import java.util.Optional;

public interface PetService {

    List<Pet> findPetByIdNotInAndPetStatus(List<Long> id, PetStatus petStatus);

    Optional<Pet> findPetById(Long id);
}
