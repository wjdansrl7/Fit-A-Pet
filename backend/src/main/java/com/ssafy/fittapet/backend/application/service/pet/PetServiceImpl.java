package com.ssafy.fittapet.backend.application.service.pet;

import static com.ssafy.fittapet.backend.common.constant.entity_field.PetStatus.*;

import com.ssafy.fittapet.backend.common.constant.entity_field.PetStatus;
import com.ssafy.fittapet.backend.domain.entity.Pet;
import com.ssafy.fittapet.backend.domain.repository.pet.PetRepository;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {
    private final PetRepository petRepository;
    private final Random rand = new Random();

    @Override
    public Pet getRandomEggPet() {
        List<Pet> eggPets = petRepository.findByPetStatus(EGG);

        // TOOD:예외처리 필요
        if (eggPets.isEmpty()) {
            return null;
        }

        return eggPets.get(rand.nextInt(eggPets.size()));
    }
}
