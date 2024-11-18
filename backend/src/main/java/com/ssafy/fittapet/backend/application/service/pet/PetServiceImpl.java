package com.ssafy.fittapet.backend.application.service.pet;

import com.ssafy.fittapet.backend.common.constant.entity_field.PetStatus;
import com.ssafy.fittapet.backend.domain.entity.Pet;
import com.ssafy.fittapet.backend.domain.repository.pet.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {
    private final PetRepository petRepository;
    @Override
    public List<Pet> findPetByIdNotInAndPetStatus(List<Long> id, PetStatus petStatus) {
        return petRepository.findByIdNotInAndPetStatus(id, petStatus);
    }

    @Override
    public Optional<Pet> findPetById(Long id) {
        return petRepository.findById(id);
    }
}
