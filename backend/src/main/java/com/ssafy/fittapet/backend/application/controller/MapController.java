package com.ssafy.fittapet.backend.application.controller;

import com.ssafy.fittapet.backend.application.service.map.MapService;
import com.ssafy.fittapet.backend.common.exception.CustomException;
import com.ssafy.fittapet.backend.domain.dto.auth.CustomOAuth2User;
import com.ssafy.fittapet.backend.domain.dto.guild.GuildJoinRequest;
import com.ssafy.fittapet.backend.domain.dto.guild.GuildRequest;
import com.ssafy.fittapet.backend.domain.dto.map.MapResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/maps")
public class MapController {

    private final MapService mapService;

    @GetMapping
    public ResponseEntity<?> getMap(@AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        List<MapResponse> map = mapService.getAll(customOAuth2User.getId());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping(path = "/create-guild")
    public ResponseEntity<?> createGuild(
            @RequestBody GuildRequest guildRequest,
            @AuthenticationPrincipal CustomOAuth2User customOAuth2User
    ) throws CustomException {
        mapService.createGuild(guildRequest, customOAuth2User.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/join-guild")
    public ResponseEntity<?> joinGuild(
            @RequestBody GuildJoinRequest guildJoinRequest,
            @AuthenticationPrincipal CustomOAuth2User customOAuth2User
    ) throws Exception {
        boolean joined = mapService.joinGuild(guildJoinRequest, customOAuth2User.getId());
        return new ResponseEntity<>(joined, HttpStatus.OK);
    }

    @DeleteMapping(path = "/guilds/{guildId}")
    public ResponseEntity<?> leaveGuild(
            @PathVariable Long guildId,
            @AuthenticationPrincipal CustomOAuth2User customOAuth2User
    ) throws CustomException {
        mapService.leaveGuild(guildId, customOAuth2User.getId());
        return new ResponseEntity<>(guildId, HttpStatus.OK);
    }
}
