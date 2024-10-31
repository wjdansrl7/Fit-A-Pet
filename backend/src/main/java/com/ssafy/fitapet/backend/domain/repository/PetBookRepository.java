package com.ssafy.fitapet.backend.domain.repository;

import com.ssafy.fitapet.backend.domain.entity.PetBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetBookRepository extends JpaRepository<PetBook, Long> {

}
