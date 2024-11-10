package com.ssafy.fittapet.backend.application.controller;

import com.ssafy.fittapet.backend.application.service.quest.QuestService;
import com.ssafy.fittapet.backend.common.constant.entity_field.QuestType;
import com.ssafy.fittapet.backend.common.exception.CustomException;
import com.ssafy.fittapet.backend.domain.dto.quest.QuestCompleteRequestDTO;
import com.ssafy.fittapet.backend.domain.dto.quest.QuestQueryRequestDTO;
import com.ssafy.fittapet.backend.domain.dto.quest.QuestResponse;
import com.ssafy.fittapet.backend.domain.entity.Quest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quests")
@Slf4j
public class QuestController {
    private final QuestService questService;

    @GetMapping(path = "/guilds")
    public ResponseEntity<?> searchGuildQuest(
            @RequestParam(value = "category", required = false) String category
    ) throws CustomException {
        List<Quest> guildQuests = questService.searchGuildQuest(category);
        return new ResponseEntity<>(guildQuests, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getMyQuestList() {
        Map<String, List<QuestResponse>> myQuestResponse = questService.getMyQuestList();
        return new ResponseEntity<>(myQuestResponse, HttpStatus.OK);
    }

    @GetMapping("/query")
    public ResponseEntity<?> queryQuest(@RequestBody QuestQueryRequestDTO dto) {
        return ResponseEntity.ok(questService.queryQuest(dto));
    }

    @PostMapping("/personal/complete")
    public ResponseEntity<?> completePersonalQuest(@RequestBody QuestCompleteRequestDTO dto) {

        log.info("QuestController completePersonalQuest");
        return ResponseEntity.ok(questService.completePersonalQuest(dto));
    }

    @PostMapping("/group/complete")
    public ResponseEntity<?> completeGuildQuest(@RequestBody QuestCompleteRequestDTO dto) {

        log.info("QuestController completeGuildQuest");
        return ResponseEntity.ok(questService.completeGuildQuest(dto));
    }
}
