package com.ssafy.fittapet.backend.application.controller;

import com.ssafy.fittapet.backend.application.service.map.MapService;
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

    @PostMapping(path = "/{groupPosition}/groups")
    public ResponseEntity<?> createGroup(
            @PathVariable String groupPosition,
            @RequestBody String groupName
    ){

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
