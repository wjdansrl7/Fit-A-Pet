package com.ssafy.fittapet.backend.application.controller;

import com.ssafy.fittapet.backend.application.service.quest.QuestService;
import com.ssafy.fittapet.backend.common.exception.CustomException;
import com.ssafy.fittapet.backend.domain.entity.Quest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quests")
public class QuestController {
    private final QuestService questService;

    @GetMapping(path = "/guilds")
    public ResponseEntity<?> searchGuildQuest(
            @RequestParam(value = "category", required = false) String category
    ) throws CustomException {
        List<Quest> guildQuests = questService.searchGuildQuest(category);
        return new ResponseEntity<>(guildQuests, HttpStatus.OK);
    }
}
