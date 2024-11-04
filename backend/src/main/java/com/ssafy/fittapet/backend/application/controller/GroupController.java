package com.ssafy.fittapet.backend.application.controller;

import com.ssafy.fittapet.backend.application.service.group.GroupService;
import com.ssafy.fittapet.backend.application.service.map.MapService;
import com.ssafy.fittapet.backend.domain.dto.group.GroupInfoResponse;
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

    @PostMapping
    public ResponseEntity<?> createGroup(
            // todo : request dto로 받을지 그냥 requestParam으로 받을지 (1)
            @RequestBody GroupRequest groupRequest
    ){
        groupService.createGroup(groupRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/{groupId}/entering-code")
    public ResponseEntity<?> getEnteringCode(
            @PathVariable Long groupId
    ){
        String enteringCode = groupService.getEnteringCode(groupId);
        return new ResponseEntity<>(enteringCode, HttpStatus.OK);
    }

    @PostMapping(path = "/join")
    public ResponseEntity<?> joinGroup(
            // todo : request dto로 받을지 그냥 requestParam으로 받을지 (2)
            @RequestParam String enteringCode,
            @RequestParam Long groupPosition
    ){
        groupService.joinGroup(enteringCode, groupPosition);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{groupId}")
    public ResponseEntity<?> leaveGroup(
            @PathVariable Long groupId
    ){
        groupService.leaveGroup(groupId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/{groupId}/group-info")
    public ResponseEntity<?> getGroupInfo(
            @PathVariable Long groupId
    ){
        GroupInfoResponse groupInfoResponse = groupService.getGroupInfo(groupId);
        return new ResponseEntity<>(groupInfoResponse, HttpStatus.OK);
    }
}
