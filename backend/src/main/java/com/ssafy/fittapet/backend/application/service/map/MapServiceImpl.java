package com.ssafy.fittapet.backend.application.service.map;

import com.ssafy.fittapet.backend.common.util.EnteringCodeUtil;
import com.ssafy.fittapet.backend.common.validator.GuildValidator;
import com.ssafy.fittapet.backend.common.validator.MapValidator;
import com.ssafy.fittapet.backend.domain.dto.guild.GuildRequest;
import com.ssafy.fittapet.backend.domain.dto.map.MapResponse;
import com.ssafy.fittapet.backend.domain.entity.Guild;
import com.ssafy.fittapet.backend.domain.entity.GuildQuest;
import com.ssafy.fittapet.backend.domain.entity.Map;
import com.ssafy.fittapet.backend.domain.entity.User;
import com.ssafy.fittapet.backend.domain.repository.Map.MapRepository;
import com.ssafy.fittapet.backend.domain.repository.guild.GuildRepository;
import com.ssafy.fittapet.backend.domain.repository.guild_quest.GuildQuestRepository;
import com.ssafy.fittapet.backend.domain.repository.user_quest.UserQuestStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<MapResponse> getAll() {
        // todo : 로그인한 유저 id 갖고오기
        Long userId = 1L;

        return guildRepository.findAllByUserId(userId);
    }

    @Override
    public void createGuild(GuildRequest guildRequest) {
        // 1. todo : 로그인한 유저 id
        Long userId = 1L;
        User user = User.builder().id(userId).build();
        // 2. position 유효 검사
        if(!mapValidator.isAblePosition(userId, guildRequest.getGuildPosition())) return;
        // 3. 그룹 이름 유효 검사?
        if(!guildValidator.isNameUnique(guildRequest.getGuildName())) return;
        // 4. 그룹 생성
        Guild guild = Guild.builder().
                guildLeader(user).
                guildName(guildRequest.getGuildName()).
                build();
        guild = guildRepository.save(guild);
        // 5. 맵 db에 정보 저장
        mapRepository.save(Map.builder().
                user(user).
                guild(guild).
                guildPosition(guildRequest.getGuildPosition()).
                build());
    }

    @Override
    public Boolean joinGuild(String enteringCode, Long guildPosition) {
        try {
            // todo : 요청자 정보 받아오기
            User user = User.builder().id(1L).build();

            // 초대 코드 기간이 유효하면 guildId, 유효하지 않으면 -1 반환
            Long guildId = EnteringCodeUtil.isCodeValid(enteringCode);
            if(guildId == -1) return false;
            // 그룹 없으면 가입 불가
            Guild guild = guildValidator.isExist(guildId).orElseThrow();
            // 이미 가입되어 있으면 가입 불가
            if(mapValidator.isAlreadyJoined(user.getId(), guildId)) return false;
            // 그룹 인원 수가 6명보다 많으면 가입 불가
            if(!mapValidator.isUnder6(guildId)) return false;
            // 위치가 유효하지 않으면 가입 불가
            if(!mapValidator.isAblePosition(user.getId(), guildPosition)) return false;
            // 조건 다 만족하면 가입
            mapRepository.save(Map.builder().
                    user(user).
                    guild(guild).
                    guildPosition(guildPosition).
                    build());

            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void leaveGuild(Long guildId) {
        // map에서 삭제
        // todo : 요청자 정보 가져오기
        User user = User.builder().id(1L).build();
        if(!mapValidator.isAlreadyJoined(user.getId(), guildId)) return;
        if(!guildValidator.isExist(guildId).equals(null)) return;

        mapRepository.deleteByGuildIdAndUserId(guildId, user.getId());

        // userQuestStatus에서 삭제
        GuildQuest guildQuest = guildQuestRepository.findByGuildId(guildId);
        if(guildQuest == null) return;
        userQuestStatusRepository.deleteByUserIdAndGuildQuestId(user.getId(), guildQuest.getId());
    }
}
