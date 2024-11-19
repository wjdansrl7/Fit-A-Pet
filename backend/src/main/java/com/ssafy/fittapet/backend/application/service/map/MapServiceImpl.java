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
        User user = userRepository.findById(userId).orElse(null);

        if (!mapValidator.isAblePosition(userId, guildRequest.getGuildPosition()))
            throw new CustomException(NOT_AVAILABLE_POSITION);
        if (!guildValidator.isNameUnique(guildRequest.getGuildName()))
            throw new CustomException(DUPLICATED_NAME);

        Guild guild = Guild.builder().
                guildLeader(user).
                guildName(guildRequest.getGuildName()).
                build();
        guild = guildRepository.save(guild);

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

        User user = userRepository.findById(userId).orElse(null);

        String enteringCode = guildJoinRequest.getEnteringCode();
        Long guildPosition = guildJoinRequest.getGuildPosition();

        Long guildId = EnteringCodeUtil.isCodeValid(enteringCode);
        if (guildId == -1) return false;
        Guild guild = guildValidator.isExist(guildId).orElseThrow(() -> new CustomException(NO_GUILD));
        assert user != null;
        if (mapValidator.isAlreadyJoined(user.getId(), guildId)) throw new CustomException(ALREADY_JOIN);
        if (!mapValidator.isUnder6(guildId)) throw new CustomException(FULL_GUILD);
        if (!mapValidator.isAblePosition(user.getId(), guildPosition))
            throw new CustomException(NOT_AVAILABLE_POSITION);

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
        User user = userRepository.findById(userId).orElse(null);
        if (guildValidator.isExist(guildId).isEmpty()) throw new CustomException(NO_GUILD);
        assert user != null;
        if (!mapValidator.isAlreadyJoined(user.getId(), guildId)) throw new CustomException(NOT_GUILD_MEMBER);

        Guild guild = guildRepository.findById(guildId).orElse(null);
        assert guild != null;
        if (Objects.equals(guild.getGuildLeader().getId(), userId)) {
            List<Map> members = mapRepository.findByGuild(guild);
            if (members.size() > 1) {
                members.sort(Comparator.comparingLong(Map::getId));
                guild.updateLeader(members.get(1).getUser());
                processGuildMembers(userId, guildId);
            } else {
                processGuildMembers(userId, guildId);
                deleteGuildAndQuest(guildId);
            }
        } else {
            processGuildMembers(userId, guildId);
        }
    }

    @Transactional
    public void processGuildMembers(Long userId, Long guildId) {
        mapRepository.deleteByGuildIdAndUserId(guildId, userId);
        GuildQuest guildQuest = guildQuestRepository.findByGuildId(guildId);
        if (guildQuest != null) {
            userQuestStatusRepository.deleteByUserIdAndGuildQuestId(userId, guildQuest.getId());
        }
    }

    @Transactional
    public void deleteGuildAndQuest(Long guildId) {
        GuildQuest guildQuest = guildQuestRepository.findByGuildId(guildId);
        if (guildQuest != null) {
            userQuestStatusRepository.deleteByGuildQuestId(guildQuest.getId());
            guildQuestRepository.delete(guildQuest);
        }
        guildRepository.deleteById(guildId);
    }
}
