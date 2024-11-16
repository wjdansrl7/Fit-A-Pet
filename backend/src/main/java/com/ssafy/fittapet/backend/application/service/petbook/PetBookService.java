package com.ssafy.fittapet.backend.application.service.petbook;

import com.ssafy.fittapet.backend.domain.entity.PetBook;
import com.ssafy.fittapet.backend.domain.entity.User;
import java.util.List;

public interface PetBookService {

    // 펫 생성
    PetBook createPetBook(User LoginUser);

    // 해당하는 id 펫 조회
    PetBook selectPetBook(Long id, User loginUser);

    // 펫 레벨 계산
    Integer[] calculateLevel(Integer petExp);

    // 유저의 모든 펫 반환
    List<PetBook> selectAllPetBook(User loginUser);

    // 퀘스트 완료 후, 경험치 추가 및 레벨 체크하여 펫의 진화가능 여부 확인, true일 때, 최고 레벨로 알 생성 가능 판단
    boolean updateExpAndEvolveCheck(PetBook petBook, Integer expGained, User loginUser);

    // 펫 닉네임 변경
    void updatePetNickname(String updatePetNickname, PetBook petBook);

}
