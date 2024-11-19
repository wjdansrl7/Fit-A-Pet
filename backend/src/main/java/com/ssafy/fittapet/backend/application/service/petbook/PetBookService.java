package com.ssafy.fittapet.backend.application.service.petbook;

import com.ssafy.fittapet.backend.domain.entity.PetBook;
import com.ssafy.fittapet.backend.domain.entity.User;

import java.util.List;

public interface PetBookService {

    void createPetBook(User LoginUser);

    PetBook findPetBookById(Long id, User loginUser);

    List<PetBook> findAllPetBooks(User loginUser);

    boolean processQuestCompletion(PetBook petBook, Integer expGained, User loginUser);

    void updatePetNickname(String updatePetNickname, PetBook petBook);
}
