package com.ssafy.fittapet.backend.application.controller;

import com.ssafy.fittapet.backend.application.service.group.GroupService;
import com.ssafy.fittapet.backend.application.service.map.MapService;
import com.ssafy.fittapet.backend.domain.dto.group.GroupRequest;
import com.ssafy.fittapet.backend.domain.dto.map.MapResponse;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;

    @GetMapping
    public ResponseEntity<?>getMap(){
        List<MapResponse> map = groupService.getAll();

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping(path = "/{groupId}/entering-code")
    public ResponseEntity<?> getEnteringCode(
            @PathVariable int groupId
    ){
        Long enteringCode = groupService.getEnteringCode(groupId);
        return new ResponseEntity<>(enteringCode, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createGroup(
            @RequestBody GroupRequest groupRequest
    ){
        groupService.createGroup(groupRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
