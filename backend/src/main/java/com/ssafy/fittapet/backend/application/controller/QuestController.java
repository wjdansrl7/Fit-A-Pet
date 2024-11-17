package com.ssafy.fittapet.backend.application.controller;

import com.ssafy.fittapet.backend.application.service.auth.AuthService;
import com.ssafy.fittapet.backend.application.service.quest.QuestService;
import com.ssafy.fittapet.backend.common.exception.CustomException;
import com.ssafy.fittapet.backend.domain.dto.auth.CustomOAuth2User;
import com.ssafy.fittapet.backend.domain.dto.quest.QuestCompleteRequest;
import com.ssafy.fittapet.backend.domain.dto.quest.QuestResponse;
import com.ssafy.fittapet.backend.domain.entity.Quest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quests")
@Slf4j
public class QuestController {
    private final QuestService questService;
    private final AuthService authService;

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

    @PostMapping("/personal/complete")
    public ResponseEntity<?> completePersonalQuest(@RequestBody QuestCompleteRequest dto,
                                                   @AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        return ResponseEntity.ok(questService.completePersonalQuest(dto, customOAuth2User.getUsername()));
    }

    @PostMapping("/guild/complete")
    public ResponseEntity<?> completeGuildQuest(@RequestBody QuestCompleteRequest dto,
                                                @AuthenticationPrincipal CustomOAuth2User customOAuth2User) throws CustomException {
        return ResponseEntity.ok(questService.completeGuildQuest(dto, customOAuth2User.getUsername()));
    }
}
