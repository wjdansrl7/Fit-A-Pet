package com.ssafy.fittapet.backend.domain.repository.pet;

import com.ssafy.fittapet.backend.common.constant.entity_field.PetStatus;
import com.ssafy.fittapet.backend.common.constant.entity_field.PetType;
import com.ssafy.fittapet.backend.domain.entity.Pet;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class PetDataInitializer {
    @Bean
    CommandLineRunner initDatabase(PetRepository petRepository) {
        return args -> {
            // 데이터가 있는지 확인하여 없을 경우에만 초기 데이터 삽입
            if (petRepository.count() == 0) {
                List<Pet> pets = List.of(
                        Pet.builder().petType(PetType.BELUGA).petStatus(PetStatus.EGG).evolutionLevel(10).build(),
                        Pet.builder().petType(PetType.BELUGA).petStatus(PetStatus.SUBADULT).evolutionLevel(20).build(),
                        Pet.builder().petType(PetType.BELUGA).petStatus(PetStatus.ADULT).evolutionLevel(30).build(),
                        Pet.builder().petType(PetType.CHINCHILLA).petStatus(PetStatus.EGG).evolutionLevel(10).build(),
                        Pet.builder().petType(PetType.CHINCHILLA).petStatus(PetStatus.SUBADULT).evolutionLevel(20).build(),
                        Pet.builder().petType(PetType.CHINCHILLA).petStatus(PetStatus.ADULT).evolutionLevel(30).build(),
                        Pet.builder().petType(PetType.LION).petStatus(PetStatus.EGG).evolutionLevel(10).build(),
                        Pet.builder().petType(PetType.LION).petStatus(PetStatus.SUBADULT).evolutionLevel(20).build(),
                        Pet.builder().petType(PetType.LION).petStatus(PetStatus.ADULT).evolutionLevel(30).build(),
                        Pet.builder().petType(PetType.WEASEL).petStatus(PetStatus.EGG).evolutionLevel(10).build(),
                        Pet.builder().petType(PetType.WEASEL).petStatus(PetStatus.SUBADULT).evolutionLevel(20).build(),
                        Pet.builder().petType(PetType.WEASEL).petStatus(PetStatus.ADULT).evolutionLevel(30).build(),
                        Pet.builder().petType(PetType.WHALE).petStatus(PetStatus.EGG).evolutionLevel(10).build(),
                        Pet.builder().petType(PetType.WEASEL).petStatus(PetStatus.SUBADULT).evolutionLevel(20).build(),
                        Pet.builder().petType(PetType.WHALE).petStatus(PetStatus.ADULT).evolutionLevel(30).build()
                );
                petRepository.saveAll(pets);
            }
        };
    }
}
