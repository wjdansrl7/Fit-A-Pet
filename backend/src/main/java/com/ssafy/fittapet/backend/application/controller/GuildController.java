package com.ssafy.fittapet.backend.application.controller;

import com.ssafy.fittapet.backend.application.service.guild.GuildService;
import com.ssafy.fittapet.backend.domain.dto.guild.GuildInfoResponse;
import com.ssafy.fittapet.backend.domain.dto.guild.GuildRequest;
import com.ssafy.fittapet.backend.domain.dto.map.MapResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/guilds")
public class GuildController {
    private final GuildService guildService;

//    @GetMapping
//    public ResponseEntity<?>getMap(){
//        List<MapResponse> map = guildService.getAll();
//
//        return new ResponseEntity<>(map, HttpStatus.OK);
//    }

//    @PostMapping
//    public ResponseEntity<?> createGuild(
//            // todo : request dto로 받을지 그냥 requestParam으로 받을지 (1)
//            @RequestBody GuildRequest guildRequest
//    ){
//        guildService.createGuild(guildRequest);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @GetMapping(path = "/{guildId}/entering-code")
    public ResponseEntity<?> getEnteringCode(
            @PathVariable Long guildId
    ){
        String enteringCode = guildService.getEnteringCode(guildId);
        return new ResponseEntity<>(enteringCode, HttpStatus.OK);
    }

//    @PostMapping(path = "/join")
//    public ResponseEntity<?> joinGuild(
//            // todo : request dto로 받을지 그냥 requestParam으로 받을지 (2)
//            @RequestParam String enteringCode,
//            @RequestParam Long guildPosition
//    ){
//        guildService.joinGuild(enteringCode, guildPosition);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

//    @DeleteMapping(path = "/{guildId}")
//    public ResponseEntity<?> leaveGuild(
//            @PathVariable Long guildId
//    ){
//        guildService.leaveGuild(guildId);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @GetMapping(path = "/{guildId}/guild-info")
    public ResponseEntity<?> getGuildInfo(
            @PathVariable Long guildId
    ){
        GuildInfoResponse guildInfoResponse = guildService.getGuildInfo(guildId);
        return new ResponseEntity<>(guildInfoResponse, HttpStatus.OK);
    }

    @PutMapping(path = "/{guildId}/quests/{questId}")
    public ResponseEntity<?> updateGuildQuest(
            @PathVariable Long guildId,
            @PathVariable Long questId
    ){
        guildService.updateGuildQuest(guildId, questId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
