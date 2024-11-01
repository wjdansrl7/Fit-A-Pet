package com.ssafy.fittapet.backend.application.service.group;

import com.ssafy.fittapet.backend.domain.dto.group.GroupRequest;
import com.ssafy.fittapet.backend.domain.dto.map.MapResponse;

import java.util.List;

public interface GroupService {
    List<MapResponse> getAll();

    void createGroup(GroupRequest groupRequest);

    Long getEnteringCode(int groupId);
}
