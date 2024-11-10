package com.ssafy.fittapet.backend.domain.repository.pet_book;

import com.ssafy.fittapet.backend.domain.entity.PetBook;
import com.ssafy.fittapet.backend.domain.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetBookRepository extends JpaRepository<PetBook, Long> {

    List<PetBook> findPetBookByUser(User user);
}
