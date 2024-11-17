package com.ssafy.fittapet.backend.domain.repository.pet_book;

import com.ssafy.fittapet.backend.common.constant.entity_field.PetStatus;
import com.ssafy.fittapet.backend.domain.entity.PetBook;
import com.ssafy.fittapet.backend.domain.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PetBookRepository extends JpaRepository<PetBook, Long> {
    @Query("SELECT pb FROM PetBook pb JOIN FETCH pb.pet WHERE pb.user =:user")
    List<PetBook> findPetBookByUser(User user);

    @Query("SELECT pb.pet.id FROM PetBook pb WHERE pb.user = :user")
    List<Long> findOwnedPetIdsByUser(User user);

    PetBook findByIdAndUser(Long id, User user);

}
