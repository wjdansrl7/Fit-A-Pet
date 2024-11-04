package com.ssafy.fittapet.backend.application.service.quest;

import com.ssafy.fittapet.backend.domain.entity.Quest;

import java.util.List;

public interface QuestService {
    List<Quest> searchGuildQuest(String category);
}
