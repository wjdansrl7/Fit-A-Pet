package com.ssafy.fittapet.backend.application.service.group;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.ssafy.fittapet.backend.application.service.map.MapService;
import com.ssafy.fittapet.backend.domain.dto.group.GroupRequest;
import com.ssafy.fittapet.backend.domain.dto.map.MapResponse;
import com.ssafy.fittapet.backend.domain.entity.Group;
import com.ssafy.fittapet.backend.domain.entity.Map;
import com.ssafy.fittapet.backend.domain.entity.User;
import com.ssafy.fittapet.backend.domain.repository.Map.MapRepository;
import com.ssafy.fittapet.backend.domain.repository.group.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final MapRepository mapRepository;

    @Override
    public List<MapResponse> getAll() {
        // todo : 로그인한 유저 id 갖고오기
        Long userId = 1L;

        return groupRepository.findAllByUserId(userId);
    }

    @Override
    public void createGroup(GroupRequest groupRequest) {
        // 1. todo : 로그인한 유저 id
        Long userId = 1L;
        User user = User.builder().id(userId).build();
        // 2. position 유효 검사
        // 3. 그룹 이름 유효 검사?
        // 4. 그룹 생성
        Group group = Group.builder().
                groupLeader(user).
                groupName(groupRequest.getGroupName()).
                build();
        group = groupRepository.save(group);
        // 5. 맵 db에 정보 저장
        mapRepository.save(Map.builder().
                user(user).
                group(group).
                groupPosition(groupRequest.getGroupPosition()).
                build());
    }

    @Override
    public Long getEnteringCode(int groupId) {
        String id = NanoIdUtils.randomNanoId();
        return 0L;
    }
}
