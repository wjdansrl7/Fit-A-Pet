package com.ssafy.fittapet.backend.domain.repository;

import com.ssafy.fittapet.backend.domain.entity.PetBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetBookRepository extends JpaRepository<PetBook, Long> {

}
