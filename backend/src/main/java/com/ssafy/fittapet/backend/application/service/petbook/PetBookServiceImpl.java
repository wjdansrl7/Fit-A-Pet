package com.ssafy.fittapet.backend.application.service.petbook;

import com.ssafy.fittapet.backend.application.service.pet.PetService;
import com.ssafy.fittapet.backend.common.constant.entity_field.PetStatus;
import com.ssafy.fittapet.backend.domain.entity.Pet;
import com.ssafy.fittapet.backend.domain.entity.PetBook;
import com.ssafy.fittapet.backend.domain.entity.User;
import com.ssafy.fittapet.backend.domain.repository.pet.PetRepository;
import com.ssafy.fittapet.backend.domain.repository.pet_book.PetBookRepository;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ssafy.fittapet.backend.common.constant.entity_field.PetStatus.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PetBookServiceImpl implements PetBookService{

    private final PetBookRepository petBookRepository;
    private final PetRepository petRepository;
    private final PetService petService;
    private final Random random = new Random();


    @Override
    public PetBook createPetBook(String petNickname, User loginUser) {

        // 사용자가 이미 소유한 펫 ID 목록 조회
        List<Long> ownedPetIds = petBookRepository.findOwnedPetIdsByUser(loginUser, EGG);

        // 사용자가 소유하지 않은 펫 목록 조회
        List<Pet> availablePets = petRepository.findByIdNotIn(ownedPetIds, EGG);

        // 소유하지 않은 펫이 없을 경우
        if (availablePets.isEmpty()) {
            // TODO: 예외처리 필요
            return null;
        }


        // 랜덤으로 하나의 펫 선택
        Pet randomEggPet = availablePets.get(random.nextInt(availablePets.size()));

        // 펫 닉네임이 없을 경우, 펫 종류로 기본 닉네임 설정
        if (petNickname == null && petNickname.isEmpty()) {
            petNickname = randomEggPet.getPetType().getValue();
        }


        PetBook petBook = PetBook.builder()
                .user(loginUser)
                .pet(randomEggPet)
                .petExp(0)
                .petNickname(petNickname)
                .build();

        return petBookRepository.save(petBook);
    }

    @Override
    public PetBook selectPetBook(Long id) {
        return petBookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 펫이 없습니다."));
    }

    public Integer[] calculateLevel(Integer petExp) {
        int petLevel = petExp / 500;
        int petPercent = (petExp % 500) / 500 * 100;

        return new Integer[]{petLevel, petPercent};
    }

    @Override
    public List<PetBook> selectAllPetBook(User loginUser) {

        return petBookRepository.findPetBookByUser(loginUser);
    }

    @Override
    public void updateExpAndEvolveCheck(PetBook petBook, Integer expGained) {
        petBook.levelUp(expGained);

        if (petBook.needsEvolution()) { // 진화 필요 조건 확인
            Optional<Pet> nextEvolutionPet = petRepository.findNextEvolution(
                    petBook.getPet().getPetType(),
                    petBook.getPet().getPetStatus(),
                    petBook.getPet().getEvolutionLevel() + 1);

            // 진화 단계의 Pet 을 업데이트
            nextEvolutionPet.ifPresent(petBook::updatePet);
        }

        petBookRepository.save(petBook); // 변경 사항을 저장
    }
}
