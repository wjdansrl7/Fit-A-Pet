package com.ssafy.fittapet.backend.domain.repository.personal_quest;

import com.ssafy.fittapet.backend.domain.entity.PersonalQuest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalQuestRepository extends JpaRepository<PersonalQuest, Long>, PersonalQuestCustomRepository {
}
