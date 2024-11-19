package com.ssafy.fittapet.backend.application.controller;

import com.ssafy.fittapet.backend.application.service.quest.QuestService;
import com.ssafy.fittapet.backend.common.exception.CustomException;
import com.ssafy.fittapet.backend.domain.dto.auth.CustomOAuth2User;
import com.ssafy.fittapet.backend.domain.dto.quest.QuestCompleteRequestDTO;
import com.ssafy.fittapet.backend.domain.dto.quest.QuestQueryRequestDTO;
import com.ssafy.fittapet.backend.domain.dto.quest.QuestResponse;
import com.ssafy.fittapet.backend.domain.entity.Quest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping
    public ResponseEntity<?> getMyQuestList(@AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        Map<String, List<QuestResponse>> myQuestResponse = questService.getMyQuestList(customOAuth2User.getId());
        return new ResponseEntity<>(myQuestResponse, HttpStatus.OK);
    }

    @GetMapping("/query")
    public ResponseEntity<?> queryQuest(@RequestBody QuestQueryRequestDTO dto) {
        return ResponseEntity.ok(questService.queryQuest(dto));
    }

    @PostMapping("/personal/complete")
    public ResponseEntity<?> completePersonalQuest(@RequestBody QuestCompleteRequestDTO dto,
                                                   @AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        return ResponseEntity.ok(questService.completePersonalQuest(dto, customOAuth2User.getId()));
    }

    @PostMapping("/guild/complete")
    public ResponseEntity<?> completeGuildQuest(@RequestBody QuestCompleteRequestDTO dto,
                                                @AuthenticationPrincipal CustomOAuth2User customOAuth2User) throws CustomException {
        return ResponseEntity.ok(questService.completeGuildQuest(dto, customOAuth2User.getId()));
    }
}
