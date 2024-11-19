package com.ssafy.fittapet.backend.application.service.petbook;

import com.ssafy.fittapet.backend.application.service.auth.AuthService;
import com.ssafy.fittapet.backend.application.service.pet.PetService;
import com.ssafy.fittapet.backend.domain.entity.Pet;
import com.ssafy.fittapet.backend.domain.entity.PetBook;
import com.ssafy.fittapet.backend.domain.entity.User;
import com.ssafy.fittapet.backend.domain.repository.auth.UserRepository;
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
    private final PetService petService;
    private final UserRepository userRepository;
    private final Random random = new Random();

    @Override
    public PetBook createPetBook(User loginUser) {
        List<Long> ownedPetIds = petBookRepository.findOwnedPetIdsByUser(loginUser);
        List<Pet> availablePets = petService.findPetByIdNotInAndPetStatus(ownedPetIds, EGG);

        if (availablePets.isEmpty()) {
            // TODO: 예외처리 필요
            return null;
        }

        Pet randomEggPet = availablePets.get(random.nextInt(availablePets.size()));

        PetBook petBook = PetBook.builder()
                .user(loginUser)
                .pet(randomEggPet)
                .petExp(0)
                .petNickname(randomEggPet.getPetType().getValue())
                .build();

        petBookRepository.save(petBook);

//        authService.updateMainPet(petBook.getId(), loginUser);
        loginUser.updatePetMainId(petBook.getId());
        userRepository.save(loginUser);

        return petBook;
    }

    @Override
    public PetBook findPetBookById(Long id, User loginUser) {
        return petBookRepository.findByIdAndUser(id, loginUser);
    }

    @Override
    public List<PetBook> findAllPetBooks(User loginUser) {
        return petBookRepository.findPetBookByUser(loginUser);
    }

    @Override
    public boolean processQuestCompletion(PetBook petBook, Integer expGained, User loginUser) {
        petBook.levelUp(expGained);

        if (petBook.getPetLevel() >= 30 && !petBook.isIssueEgg()) {
            this.createPetBook(loginUser);
            petBook.updateIssueEgg(true);
            petBookRepository.save(petBook);
            return true;
        }

        if (petBook.needsEvolution()) {
            Long nextPetId = calculateNextPetId(petBook.getPet().getId(), petBook.getPet().getEvolutionLevel());
            Optional<Pet> nextEvolutionPet = petService.findPetById(nextPetId);
            nextEvolutionPet.ifPresent(petBook::updatePet);
        }
        petBookRepository.save(petBook);
        return false;
    }
    private Long calculateNextPetId(Long currentPetId, int currentLevel) {
        if (currentLevel == 10) {
            return currentPetId + 1;
        } else if (currentLevel == 20) {
            return currentPetId + 1;
        }
        return currentPetId;
    }
    public void updatePetNickname(String updatePetNickname, PetBook petBook) {
        PetBook updatingPetBook = petBookRepository.findById(petBook.getId()).orElseThrow(() -> new IllegalArgumentException("해당하는 펫이 없습니다."));
        updatingPetBook.updatePetNickname(updatePetNickname);
        petBookRepository.save(updatingPetBook);
    }
}
