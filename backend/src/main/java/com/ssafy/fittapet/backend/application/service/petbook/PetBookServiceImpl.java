package com.ssafy.fittapet.backend.application.service.petbook;

import com.ssafy.fittapet.backend.application.service.pet.PetService;
import com.ssafy.fittapet.backend.common.constant.entity_field.PetStatus;
import com.ssafy.fittapet.backend.domain.entity.Pet;
import com.ssafy.fittapet.backend.domain.entity.PetBook;
import com.ssafy.fittapet.backend.domain.entity.User;
import com.ssafy.fittapet.backend.domain.repository.auth.UserRepository;
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
    private final UserRepository userRepository;
    private final PetService petService;
    private final Random random = new Random();


    @Override
    public PetBook createPetBook(User loginUser) {

        // 사용자가 이미 소유한 펫 ID 목록 조회
        List<Long> ownedPetIds = petBookRepository.findOwnedPetIdsByUser(loginUser);
        // 사용자가 소유하지 않은 펫 목록 조회
        List<Pet> availablePets = petRepository.findByIdNotInAndPetStatus(ownedPetIds, EGG);

        // 소유하지 않은 펫이 없을 경우
        if (availablePets.isEmpty()) {
            // TODO: 예외처리 필요
            return null;
        }

        // 랜덤으로 하나의 펫 선택
        Pet randomEggPet = availablePets.get(random.nextInt(availablePets.size()));

        PetBook petBook = PetBook.builder()
                .user(loginUser)
                .pet(randomEggPet)
                .petExp(0)
                .petNickname(randomEggPet.getPetType().toString()) // 기본 펫 타입 이름으로 지정
                .build();

        petBookRepository.save(petBook);

        // 처음 알이 등록되면 로그인한 유저의 대표 캐릭터 변경
        loginUser.updatePetMainId(petBook.getId());

        // TODO: 서비스단으로 빼야됨
        userRepository.save(loginUser);

        return petBook;
    }

    @Override
    public PetBook selectPetBook(Long id, User loginUser) {
        return petBookRepository.findByIdAndUser(id, loginUser);
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
    public boolean updateExpAndEvolveCheck(PetBook petBook, Integer expGained, User loginUser) {
        petBook.levelUp(expGained);

        // 최고 레벨에 도달했는지 확인
        if (petBook.getPetLevel() >= 30 && !petBook.isIssueEgg()) {
            // 알 발급가능한 상태이므로, 알을 발급받는다.
            this.createPetBook(loginUser);
            petBook.updateIssueEgg(true);
            petBookRepository.save(petBook);
            System.out.println(petBook.isIssueEgg());
            return true;
        }
        // 진화 필요 조건 확인
        if (petBook.needsEvolution()) {
            // 다음 진화 펫의 ID 계산
            Long nextPetId = calculateNextPetId(petBook.getPet().getId(), petBook.getPet().getEvolutionLevel());

            // 다음 진화 Pet 검색
            Optional<Pet> nextEvolutionPet = petRepository.findById(nextPetId);

            // 진화 단계의 Pet 업데이트
            nextEvolutionPet.ifPresent(petBook::updatePet);
        }
        petBookRepository.save(petBook);
        return false;
    }



    private Long calculateNextPetId(Long currentPetId, int currentLevel) {
        // 레벨에 따른 다음 진화 Pet ID 계산
        if (currentLevel == 10) {
            return currentPetId + 1; // 준성체
        } else if (currentLevel == 20) {
            return currentPetId + 1; // 성체
        }
        return currentPetId; // 기본적으로 현재 ID 반환
    }


    public void updatePetNickname(String updatePetNickname, PetBook petBook) {
        PetBook updatingPetBook = petBookRepository.findById(petBook.getId()).orElseThrow(() -> new IllegalArgumentException("해당하는 펫이 없습니다."));
        log.info(updatingPetBook.getPetNickname());
        updatingPetBook.updatePetNickname(updatePetNickname);

        petBookRepository.save(updatingPetBook);
    }
}
