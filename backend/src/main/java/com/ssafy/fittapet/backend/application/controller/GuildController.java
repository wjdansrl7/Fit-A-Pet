package com.ssafy.fittapet.backend.application.controller;

import com.ssafy.fittapet.backend.application.service.guild.GuildService;
import com.ssafy.fittapet.backend.common.exception.CustomException;
import com.ssafy.fittapet.backend.domain.dto.auth.CustomOAuth2User;
import com.ssafy.fittapet.backend.domain.dto.guild.GuildInfoResponse;
import com.ssafy.fittapet.backend.domain.dto.guild.GuildMemberInfoResponse;
import com.ssafy.fittapet.backend.domain.dto.guild.GuildQuestInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/guilds")
public class GuildController {

    private final GuildService guildService;

    @GetMapping(path = "/{guildId}/entering-code")
    public ResponseEntity<?> getEnteringCode(
            @AuthenticationPrincipal CustomOAuth2User customOAuth2User,
            @PathVariable Long guildId
    ){
        String enteringCode = guildService.getEnteringCode(guildId, customOAuth2User.getId());
        return new ResponseEntity<>(enteringCode, HttpStatus.OK);
    }

    @GetMapping(path = "/{guildId}/guild-info")
    public ResponseEntity<?> getGuildInfo(
            @PathVariable Long guildId
    ) throws CustomException {
        GuildInfoResponse guildInfo = guildService.getGuildInfo(guildId);
        return new ResponseEntity<>(guildInfo, HttpStatus.OK);
    }

    @GetMapping(path = "/{guildId}/member-info")
    public ResponseEntity<?> getMemberInfo(
            @PathVariable Long guildId
    ) throws CustomException {
        List<GuildMemberInfoResponse> members = guildService.getMemberInfo(guildId);
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @GetMapping(path = "/{guildId}/quest-info")
    public ResponseEntity<?> getQuestInfo(
            @PathVariable Long guildId
    ) throws CustomException {
        GuildQuestInfoResponse guildQuest = guildService.getQuestInfo(guildId);
        return new ResponseEntity<>(guildQuest, HttpStatus.OK);
    }

    @PutMapping(path = "/{guildId}/quests/{questId}")
    public ResponseEntity<?> updateGuildQuest(
            @PathVariable Long guildId,
            @PathVariable Long questId
    ) throws CustomException {
        guildService.updateGuildQuest(guildId, questId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
