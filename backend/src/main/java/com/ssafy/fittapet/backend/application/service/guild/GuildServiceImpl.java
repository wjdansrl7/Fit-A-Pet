package com.ssafy.fittapet.backend.application.service.guild;

import com.ssafy.fittapet.backend.common.exception.CustomException;
import com.ssafy.fittapet.backend.common.util.EnteringCodeUtil;
import com.ssafy.fittapet.backend.common.validator.GuildValidator;
import com.ssafy.fittapet.backend.common.validator.QuestValidator;
import com.ssafy.fittapet.backend.domain.dto.guild.GuildInfoResponse;
import com.ssafy.fittapet.backend.domain.dto.guild.GuildMemberInfoResponse;
import com.ssafy.fittapet.backend.domain.dto.guild.GuildQuestInfoResponse;
import com.ssafy.fittapet.backend.domain.entity.*;
import com.ssafy.fittapet.backend.domain.repository.guild.GuildRepository;
import com.ssafy.fittapet.backend.domain.repository.guild_quest.GuildQuestRepository;
import com.ssafy.fittapet.backend.domain.repository.map.MapRepository;
import com.ssafy.fittapet.backend.domain.repository.user_quest.UserQuestStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.ssafy.fittapet.backend.common.constant.error_code.GuildErrorCode.*;
import static com.ssafy.fittapet.backend.common.constant.error_code.QuestErrorCode.NO_QUEST;

@Service
@RequiredArgsConstructor
public class GuildServiceImpl implements GuildService {

    private final GuildRepository guildRepository;
    private final GuildQuestRepository guildQuestRepository;
    private final UserQuestStatusRepository userQuestStatusRepository;
    private final MapRepository mapRepository;

    private final QuestValidator questValidator;
    private final GuildValidator guildValidator;


    @Override
    public String getEnteringCode(Long guildId, Long userId) {
        try {
            Optional<Guild> guild = guildRepository.findById(guildId);
            if (guild.isEmpty()) throw new CustomException(NO_GUILD);
            if (!guildValidator.isGuildLeader(guildId, userId)) throw new CustomException(NOT_GUILD_LEADER);
            return EnteringCodeUtil.encrypt(guildId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GuildInfoResponse getGuildInfo(Long guildId) throws CustomException {
        if (guildValidator.isExist(guildId).isEmpty()) throw new CustomException(NO_GUILD);
        return guildRepository.findInfoById(guildId);
    }

    @Override
    public void updateGuildQuest(Long guildId, Long questId) throws CustomException {
        Guild guild = guildValidator.isExist(guildId).orElseThrow(() -> new CustomException(NO_GUILD));
        Quest quest = questValidator.isExist(questId).orElseThrow(() -> new CustomException(NO_QUEST));

        GuildQuest guildQuest = guildQuestRepository.findByGuildId(guildId);
        if (guildQuest == null) {
            guildQuest = guildQuestRepository.save(GuildQuest.builder().
                    guild(guild).
                    quest(quest).
                    build());
            List<Map> list = mapRepository.findByGuild(guild);
            for (Map map : list) {
                userQuestStatusRepository.save(UserQuestStatus.builder().
                        questStatus(false).
                        guildQuest(guildQuest).
                        user(map.getUser()).
                        build());
            }
        } else {
            if (guildQuest.getQuest().getId().equals(questId)) throw new CustomException(ALREADY_SET_QUEST);
            guildQuest.setQuest(quest);
            guildQuest = guildQuestRepository.save(guildQuest);
            List<UserQuestStatus> list = userQuestStatusRepository.findByGuildQuest(guildQuest);
            for (UserQuestStatus userQuestStatus : list) {
                userQuestStatus.updateStatus(false);
            }

            userQuestStatusRepository.saveAll(list);
        }
    }

    @Override
    public List<GuildMemberInfoResponse> getMemberInfo(Long guildId) throws CustomException {
        Guild guild = guildValidator.isExist(guildId).orElseThrow(() -> new CustomException(NO_GUILD));
        return guildRepository.findAllMemberByGuild(guild.getId());
    }

    @Override
    public GuildQuestInfoResponse getQuestInfo(Long guildId) throws CustomException {
        guildValidator.isExist(guildId).orElseThrow(() -> new CustomException(NO_GUILD));
        return guildQuestRepository.findQuestInfoByGuildId(guildId);
    }
}
