package com.ssafy.fittapet.backend.application.service.group;

import com.ssafy.fittapet.backend.common.util.EnteringCodeUtil;
import com.ssafy.fittapet.backend.domain.dto.group.GroupInfoResponse;
import com.ssafy.fittapet.backend.domain.dto.group.GroupRequest;
import com.ssafy.fittapet.backend.domain.dto.map.MapResponse;
import com.ssafy.fittapet.backend.domain.entity.Group;
import com.ssafy.fittapet.backend.domain.entity.GroupQuest;
import com.ssafy.fittapet.backend.domain.entity.Map;
import com.ssafy.fittapet.backend.domain.entity.User;
import com.ssafy.fittapet.backend.domain.repository.Map.MapRepository;
import com.ssafy.fittapet.backend.domain.repository.group.GroupRepository;
import com.ssafy.fittapet.backend.domain.repository.group_quest.GroupQuestRepository;
import com.ssafy.fittapet.backend.domain.repository.user_quest.UserQuestStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final MapRepository mapRepository;
    private final GroupQuestRepository groupQuestRepository;
    private final UserQuestStatusRepository userQuestStatusRepository;

    private final EnteringCodeUtil enteringCodeUtil;

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
    public String getEnteringCode(Long groupId) {
        // 생성날짜 + 그룹 id로 인코딩된 코드 받아오기
        try {
            // todo : 요청자가 그룹장인지 validation check
            return enteringCodeUtil.encrypt(groupId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean joinGroup(String enteringCode, Long groupPosition) {
        try {
            // todo : 요청자 정보 받아오기
            User user = User.builder().id(1L).build();

            // 초대 코드 기간이 유효하면 groupId, 유효하지 않으면 -1 반환
            Long groupId = enteringCodeUtil.isCodeValid(enteringCode);
            if(groupId == -1) return false;

            // 그룹 인원 수가 6명보다 많으면 가입 불가
            if(mapRepository.countByGroupId(groupId)==6) return false;

            // 그룹 없으면 가입 불가
            Group group = groupRepository.findById(groupId).orElse(null);
            if(group == null) return false;

            // 조건 다 만족하면 가입
            mapRepository.save(Map.builder().
                    user(user).
                    group(group).
                    groupPosition(groupPosition).
                    build());

            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void leaveGroup(Long groupId) {
        // map에서 삭제
        // todo : 요청자 정보 가져오기
        User user = User.builder().id(1L).build();
        mapRepository.deleteByGroupIdAndUserId(groupId, user.getId());

        // userQuestStatus에서 삭제
        GroupQuest groupQuest = groupQuestRepository.findByGroupId(groupId);
        if(groupQuest == null) return;
        userQuestStatusRepository.deleteByUserIdAndGroupRequestId(user.getId(), groupQuest.getId());
    }

    @Override
    public GroupInfoResponse getGroupInfo(Long groupId) {
        return groupRepository.findInfoById(groupId);
    }
}
