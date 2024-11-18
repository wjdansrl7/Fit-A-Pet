package com.ssafy.fittapet.backend.application.service.map;

import com.ssafy.fittapet.backend.common.exception.CustomException;
import com.ssafy.fittapet.backend.common.util.EnteringCodeUtil;
import com.ssafy.fittapet.backend.common.validator.GuildValidator;
import com.ssafy.fittapet.backend.common.validator.MapValidator;
import com.ssafy.fittapet.backend.domain.dto.guild.GuildJoinRequest;
import com.ssafy.fittapet.backend.domain.dto.guild.GuildRequest;
import com.ssafy.fittapet.backend.domain.dto.map.MapResponse;
import com.ssafy.fittapet.backend.domain.entity.*;
import com.ssafy.fittapet.backend.domain.repository.auth.UserRepository;
import com.ssafy.fittapet.backend.domain.repository.guild.GuildRepository;
import com.ssafy.fittapet.backend.domain.repository.guild_quest.GuildQuestRepository;
import com.ssafy.fittapet.backend.domain.repository.map.MapRepository;
import com.ssafy.fittapet.backend.domain.repository.user_quest.UserQuestStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static com.ssafy.fittapet.backend.common.constant.error_code.GuildErrorCode.*;

@Service
@RequiredArgsConstructor
public class MapServiceImpl implements MapService {
    private final MapRepository mapRepository;
    private final GuildRepository guildRepository;
    private final GuildQuestRepository guildQuestRepository;
    private final UserQuestStatusRepository userQuestStatusRepository;

    private final EnteringCodeUtil enteringCodeUtil;

    private final MapValidator mapValidator;
    private final GuildValidator guildValidator;
    private final UserRepository userRepository;

    @Override
    public List<MapResponse> getAll(Long userId) {

        return guildRepository.findAllByUserId(userId);
    }

    @Override
    @Transactional
    public void createGuild(GuildRequest guildRequest, Long userId) throws CustomException {
        // 1. todo : 로그인한 유저 id
//        Long userId = 1L;
//        User user = User.builder().id(userId).build();
//        User user = userRepository.findById(1L).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
//        Long userId = user.getId();
        // 2. position 유효 검사
        if (!mapValidator.isAblePosition(userId, guildRequest.getGuildPosition()))
            throw new CustomException(NOT_AVAILABLE_POSITION);
        // 3. 그룹 이름 유효 검사?
        if (!guildValidator.isNameUnique(guildRequest.getGuildName())) throw new CustomException(DUPLICATED_NAME);
        // 4. 그룹 생성
        Guild guild = Guild.builder().
                guildLeader(user).
                guildName(guildRequest.getGuildName()).
                build();
        guild = guildRepository.save(guild);
        // 5. 맵 db에 정보 저장
        Map map = Map.builder().
                user(user).
                guild(guild).
                guildPosition(guildRequest.getGuildPosition()).
                build();
        mapRepository.save(map);
    }

    @Override
    @Transactional
    public Boolean joinGuild(GuildJoinRequest guildJoinRequest, Long userId) throws Exception {

        // todo : 요청자 정보 받아오기
        User user = userRepository.findById(userId).orElse(null);

        String enteringCode = guildJoinRequest.getEnteringCode();
        Long guildPosition = guildJoinRequest.getGuildPosition();

        // 초대 코드 기간이 유효하면 guildId, 유효하지 않으면 -1 반환
        Long guildId = EnteringCodeUtil.isCodeValid(enteringCode);
        if (guildId == -1) return false;
        // 길드 없으면 가입 불가
        Guild guild = guildValidator.isExist(guildId).orElseThrow(() -> new CustomException(NO_GUILD));
        // 이미 가입되어 있으면 가입 불가
        if (mapValidator.isAlreadyJoined(user.getId(), guildId)) throw new CustomException(ALREADY_JOIN);
        // 그룹 인원 수가 6명보다 많으면 가입 불가
        if (!mapValidator.isUnder6(guildId)) throw new CustomException(FULL_GUILD);
        // 위치가 유효하지 않으면 가입 불가
        if (!mapValidator.isAblePosition(user.getId(), guildPosition))
            throw new CustomException(NOT_AVAILABLE_POSITION);

        // 조건 다 만족하면 가입
        mapRepository.save(Map.builder().
                user(user).
                guild(guild).
                guildPosition(guildPosition).
                build());

        GuildQuest guildQuest = guildQuestRepository.findByGuildId(guildId);
        if (guildQuest != null) {
            userQuestStatusRepository.save(UserQuestStatus.builder().
                    questStatus(false).
                    guildQuest(guildQuest).
                    user(user).
                    build());
        }

        return true;
    }

    @Override
    @Transactional
    public void leaveGuild(Long guildId, Long userId) throws CustomException {
        // map에서 삭제
        // todo : 요청자 정보 가져오기
        User user = userRepository.findById(userId).orElse(null);
        if (guildValidator.isExist(guildId).isEmpty()) throw new CustomException(NO_GUILD);
        if (!mapValidator.isAlreadyJoined(user.getId(), guildId)) throw new CustomException(NOT_GUILD_MEMBER);
//        if (guildValidator.isGuildLeader(guildId, userId)) throw new CustomException(LEADER_CANNOT_EXIT);

        // 탈퇴하는 사람이 길드장
        Guild guild = guildRepository.findById(guildId).orElse(null);
        if (Objects.equals(guild.getGuildLeader().getId(), userId)) {
            List<Map> members = mapRepository.findByGuild(guild);
            // 길드원이 두명 이상이면 가장 오래된 길드원에게 리더를 넘긴다.
            if (members.size() > 1) {
                members.sort(Comparator.comparingLong(Map::getId));
                guild.updateLeader(members.get(1).getUser());
                processGuildMembers(userId, guildId);
            } else {
                processGuildMembers(userId, guildId);
                guildRepository.delete(guild);
            }
        }
    }

    @Transactional
    public void processGuildMembers(Long userId, Long guildId) {
        mapRepository.deleteByGuildIdAndUserId(guildId, userId);
        GuildQuest guildQuest = guildQuestRepository.findByGuildId(guildId);
        if (guildQuest != null)
            userQuestStatusRepository.deleteByUserIdAndGuildQuestId(userId, guildQuest.getId());
    }
}
