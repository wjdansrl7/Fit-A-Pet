package com.ssafy.fittapet.backend.application.service.group;

import com.ssafy.fittapet.backend.domain.dto.group.GroupInfoResponse;
import com.ssafy.fittapet.backend.domain.dto.group.GroupRequest;
import com.ssafy.fittapet.backend.domain.dto.map.MapResponse;

import java.util.List;

public interface GroupService {
    List<MapResponse> getAll();

    void createGroup(GroupRequest groupRequest);

    String getEnteringCode(Long groupId);

    Boolean joinGroup(String enteringCode, Long groupPosition);

    void leaveGroup(Long groupId);

    GroupInfoResponse getGroupInfo(Long groupId);
}
