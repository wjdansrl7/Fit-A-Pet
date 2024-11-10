package com.ssafy.fittapet.backend.application.service.petbook;

import com.ssafy.fittapet.backend.domain.entity.PetBook;
import com.ssafy.fittapet.backend.domain.entity.User;
import java.util.List;

public interface PetBookService {

    // 펫 생성
    PetBook createPetBook(String petNickname, User LoginUser);

    // 해당하는 id 펫 조회
    PetBook selectPetBook(Long id);

    // 펫 레벨 계산
    Integer[] calculateLevel(Integer petExp);


    List<PetBook> selectAllPetBook(User loginUser);

    // 퀘스트 완료 후, 경험치 추가 및 레벨 체크하여 펫의 진화가능 여부 확인
    void updateExpAndEvolveCheck(PetBook petBook, Integer expGained);

}
