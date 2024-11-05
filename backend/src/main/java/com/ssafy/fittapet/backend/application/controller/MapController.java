package com.ssafy.fittapet.backend.application.controller;

import com.ssafy.fittapet.backend.application.service.map.MapService;
import com.ssafy.fittapet.backend.domain.dto.guild.GuildRequest;
import com.ssafy.fittapet.backend.domain.dto.map.MapResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/maps")
public class MapController {
    private final MapService mapService;

    @GetMapping
    public ResponseEntity<?>getMap(){
        List<MapResponse> map = mapService.getAll();

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping(path = "/create-guild")
    public ResponseEntity<?> createGuild(
            // todo : request dto로 받을지 그냥 requestParam으로 받을지 (1)
            @RequestBody GuildRequest guildRequest
    ){
        mapService.createGuild(guildRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/join-guild")
    public ResponseEntity<?> joinGuild(
            // todo : request dto로 받을지 그냥 requestParam으로 받을지 (2)
            @RequestParam String enteringCode,
            @RequestParam Long guildPosition
    ){
        mapService.joinGuild(enteringCode, guildPosition);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{guildId}")
    public ResponseEntity<?> leaveGuild(
            @PathVariable Long guildId
    ){
        mapService.leaveGuild(guildId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
