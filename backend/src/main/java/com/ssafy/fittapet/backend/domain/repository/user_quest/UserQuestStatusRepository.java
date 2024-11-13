package com.ssafy.fittapet.backend.domain.repository.user_quest;

import com.ssafy.fittapet.backend.domain.entity.GuildQuest;
import com.ssafy.fittapet.backend.domain.entity.UserQuestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserQuestStatusRepository extends JpaRepository<UserQuestStatus, Long>, UserQuestStatusCustomRepository {

    void deleteByUserIdAndGuildQuestId(Long userId, Long guildQuestId);

    List<UserQuestStatus> findByGuildQuest(GuildQuest guildQuest);
}
