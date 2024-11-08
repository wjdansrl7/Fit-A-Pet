package com.ssafy.fittapet.backend.application.service.petbook;

import com.ssafy.fittapet.backend.domain.entity.PetBook;
import com.ssafy.fittapet.backend.domain.entity.User;

public interface PetBookService {

    void createPetBook(PetBook petBook, User LoginUser);
}
