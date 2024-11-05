package com.ssafy.fittapet.backend.application.service.guild;

import com.ssafy.fittapet.backend.common.util.EnteringCodeUtil;
import com.ssafy.fittapet.backend.common.validator.GuildValidator;
import com.ssafy.fittapet.backend.common.validator.QuestValidator;
import com.ssafy.fittapet.backend.domain.dto.guild.GuildInfoResponse;
import com.ssafy.fittapet.backend.domain.entity.Guild;
import com.ssafy.fittapet.backend.domain.entity.GuildQuest;
import com.ssafy.fittapet.backend.domain.entity.Quest;
import com.ssafy.fittapet.backend.domain.repository.guild.GuildRepository;
import com.ssafy.fittapet.backend.domain.repository.guild_quest.GuildQuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuildServiceImpl implements GuildService {
    private final GuildRepository guildRepository;
    private final GuildQuestRepository guildQuestRepository;

    private final GuildValidator guildValidator;
    private final QuestValidator questValidator;

//    @Override
//    public List<MapResponse> getAll() {
//        // todo : 로그인한 유저 id 갖고오기
//        Long userId = 1L;
//
//        return guildRepository.findAllByUserId(userId);
//    }

//    @Override
//    public void createGuild(GuildRequest guildRequest) {
//        // 1. todo : 로그인한 유저 id
//        Long userId = 1L;
//        User user = User.builder().id(userId).build();
//        // 2. position 유효 검사
//        if(!mapValidator.isAblePosition(userId, guildRequest.getGuildPosition())) return;
//        // 3. 그룹 이름 유효 검사?
//        if(!guildValidator.isNameUnique(guildRequest.getGuildName())) return;
//        // 4. 그룹 생성
//        Guild guild = Guild.builder().
//                guildLeader(user).
//                guildName(guildRequest.getGuildName()).
//                build();
//        guild = guildRepository.save(guild);
//        // 5. 맵 db에 정보 저장
//        mapRepository.save(Map.builder().
//                user(user).
//                guild(guild).
//                guildPosition(guildRequest.getGuildPosition()).
//                build());
//    }

    @Override
    public String getEnteringCode(Long guildId) {
        // 생성날짜 + 그룹 id로 인코딩된 코드 받아오기
        try {
            // todo : 요청자가 그룹장인지 validation check
            if(!guildValidator.isGuildLeader(guildId, 1L)) return null;
            return EnteringCodeUtil.encrypt(guildId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    @Override
//    public Boolean joinGuild(String enteringCode, Long guildPosition) {
//        try {
//            // todo : 요청자 정보 받아오기
//            User user = User.builder().id(1L).build();
//
//            // 초대 코드 기간이 유효하면 guildId, 유효하지 않으면 -1 반환
//            Long guildId = enteringCodeUtil.isCodeValid(enteringCode);
//            if(guildId == -1) return false;
//            // 그룹 없으면 가입 불가
//            Guild guild = guildValidator.isExist(guildId).orElseThrow();
//            // 이미 가입되어 있으면 가입 불가
//            if(mapValidator.isAlreadyJoined(user.getId(), guildId)) return false;
//            // 그룹 인원 수가 6명보다 많으면 가입 불가
//            if(!mapValidator.isUnder6(guildId)) return false;
//            // 위치가 유효하지 않으면 가입 불가
//            if(!mapValidator.isAblePosition(user.getId(), guildPosition)) return false;
//            // 조건 다 만족하면 가입
//            mapRepository.save(Map.builder().
//                    user(user).
//                    guild(guild).
//                    guildPosition(guildPosition).
//                    build());
//
//            return true;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

//    @Override
//    public void leaveGuild(Long guildId) {
//        // map에서 삭제
//        // todo : 요청자 정보 가져오기
//        User user = User.builder().id(1L).build();
//        if(!mapValidator.isAlreadyJoined(user.getId(), guildId)) return;
//        if(!guildValidator.isExist(guildId).equals(null)) return;
//
//        mapRepository.deleteByGuildIdAndUserId(guildId, user.getId());
//
//        // userQuestStatus에서 삭제
//        GuildQuest guildQuest = guildQuestRepository.findByGuildId(guildId);
//        if(guildQuest == null) return;
//        userQuestStatusRepository.deleteByUserIdAndGuildQuestId(user.getId(), guildQuest.getId());
//    }

    @Override
    public GuildInfoResponse getGuildInfo(Long guildId) {
        if(guildValidator.isExist(guildId)==null) return null;
        return guildRepository.findInfoById(guildId);
    }

    @Override
    public void updateGuildQuest(Long guildId, Long questId) {
        // validation
        // todo : 반환할 Excetipion
        Guild guild = guildValidator.isExist(guildId).orElseThrow();
        Quest quest = questValidator.isExist(questId).orElseThrow();

        GuildQuest guildQuest = guildQuestRepository.findByGuildId(guildId);
        if(guildQuest == null){
            guildQuestRepository.save(GuildQuest.builder().
                    guild(guild).
                    quest(quest).
                    build());
        }
        else{
            guildQuest.setQuest(quest);
            guildQuestRepository.save(guildQuest);
        }
        // todo : 유저 퀘스트 상태 테이블 실제 활동 정보로 초기화
    }
}
