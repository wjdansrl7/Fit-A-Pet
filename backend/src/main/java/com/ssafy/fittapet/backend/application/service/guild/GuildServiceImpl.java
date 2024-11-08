package com.ssafy.fittapet.backend.application.service.guild;

import com.ssafy.fittapet.backend.common.exception.CustomException;
import com.ssafy.fittapet.backend.common.util.EnteringCodeUtil;
import com.ssafy.fittapet.backend.common.validator.GuildValidator;
import com.ssafy.fittapet.backend.common.validator.QuestValidator;
import com.ssafy.fittapet.backend.domain.dto.guild.GuildInfoResponse;
import com.ssafy.fittapet.backend.domain.dto.guild.GuildMemberInfoResponse;
import com.ssafy.fittapet.backend.domain.dto.guild.GuildQuestInfoResponse;
import com.ssafy.fittapet.backend.domain.entity.Guild;
import com.ssafy.fittapet.backend.domain.entity.GuildQuest;
import com.ssafy.fittapet.backend.domain.entity.Quest;
import com.ssafy.fittapet.backend.domain.repository.guild.GuildRepository;
import com.ssafy.fittapet.backend.domain.repository.guild_quest.GuildQuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ssafy.fittapet.backend.common.constant.error_code.GuildErrorCode.*;
import static com.ssafy.fittapet.backend.common.constant.error_code.QuestErrorCode.NO_QUEST;

@Service
@RequiredArgsConstructor
public class GuildServiceImpl implements GuildService {
    private final GuildRepository guildRepository;
    private final GuildQuestRepository guildQuestRepository;

    private final GuildValidator guildValidator;
    private final QuestValidator questValidator;


    @Override
    public String getEnteringCode(Long guildId) {
        // 생성날짜 + 그룹 id로 인코딩된 코드 받아오기
        try {
            // todo : 길드 validation
            if(!guildValidator.isGuildLeader(guildId, 1L)) throw new CustomException(NOT_GUILD_LEADER);
            return EnteringCodeUtil.encrypt(guildId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GuildInfoResponse getGuildInfo(Long guildId) throws CustomException {
        // todo : 길드원 validation
        if(guildValidator.isExist(guildId)==null) throw new CustomException(NO_GUILD);
        return guildRepository.findInfoById(guildId);
    }

    @Override
    public void updateGuildQuest(Long guildId, Long questId) throws CustomException {
        // validation
        // todo : 길드장 validation
        // 길드 존재하는지
        Guild guild = guildValidator.isExist(guildId).orElseThrow(()-> new CustomException(NO_GUILD));
        // 퀘스트 존재하는지
        Quest quest = questValidator.isExist(questId).orElseThrow(() -> new CustomException(NO_QUEST));

        // GuildQuest 테이블 확인
        GuildQuest guildQuest = guildQuestRepository.findByGuildId(guildId);
        if(guildQuest == null){
            guildQuestRepository.save(GuildQuest.builder().
                    guild(guild).
                    quest(quest).
                    build());
        }
        else{
            if(guildQuest.getQuest().getId().equals(questId)) throw new CustomException(ALREADY_SET_QUEST);
            guildQuest.setQuest(quest);
            guildQuestRepository.save(guildQuest);
        }
        // todo : 유저 퀘스트 상태 테이블 실제 활동 정보로 초기화
    }

    @Override
    public List<GuildMemberInfoResponse> getMemberInfo(Long guildId) throws CustomException {
        // todo : 길드원 validation
        Guild guild = guildValidator.isExist(guildId).orElseThrow(() -> new CustomException(NO_GUILD));

        return guildRepository.findAllMemberByGuild(guild.getId());
    }

    @Override
    public GuildQuestInfoResponse getQuestInfo(Long guildId) throws CustomException {
        // todo : 길드원 validation
        Guild guild = guildValidator.isExist(guildId).orElseThrow(() -> new CustomException(NO_GUILD));

        return guildQuestRepository.findQuestInfoByGuildId(guildId);
    }
}
