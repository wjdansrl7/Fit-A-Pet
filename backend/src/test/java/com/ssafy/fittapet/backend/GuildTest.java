//package com.ssafy.fittapet.backend;
//
//import com.ssafy.fittapet.backend.application.service.guild.GuildService;
//import com.ssafy.fittapet.backend.common.util.EnteringCodeUtil;
//import com.ssafy.fittapet.backend.common.validator.GuildValidator;
//import com.ssafy.fittapet.backend.common.validator.MapValidator;
//import com.ssafy.fittapet.backend.domain.dto.guild.GuildRequest;
//import com.ssafy.fittapet.backend.domain.entity.Guild;
//import com.ssafy.fittapet.backend.domain.entity.Map;
//import com.ssafy.fittapet.backend.domain.entity.User;
//import com.ssafy.fittapet.backend.domain.repository.Map.MapRepository;
//import com.ssafy.fittapet.backend.domain.repository.UserRepository;
//import com.ssafy.fittapet.backend.domain.repository.guild.GuildRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//@SpringBootTest
//@Transactional
//public class GuildTest {
//
//    @Autowired
//    private GuildService guildService;
//    @Autowired
//    private MapRepository mapRepository;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private GuildRepository guildRepository;
//    @Autowired
//    private EnteringCodeUtil enteringCodeUtil;
//    @Autowired
//    private MapValidator mapValidator;
//    @Autowired
//    private GuildValidator guildValidator;
//
//    @Test
//    @DisplayName("길드 생성")
//    @Rollback(true)
//    public void testCreateGuild() {
//        // Given
//        Long userId = 1L;
//        User user = userRepository.findById(userId).orElseThrow();
//
//        GuildRequest guildRequest = new GuildRequest();
//        guildRequest.setGuildName("Test");
//        guildRequest.setGuildPosition(1L);
//
//        // When
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
//        Map map = mapRepository.save(Map.builder().
//                user(user).
//                guild(guild).
//                guildPosition(guildRequest.getGuildPosition()).
//                build());
//
//        // Then
//        Assertions.assertNotNull(guild, "길드 생성 완료");
//        Assertions.assertNotNull(map, "맵에 기록 완료");
//    }
//
//    @Test
//    @DisplayName("길드 생성")
//    @Rollback(false)
//    public void testJoinGuild() throws Exception {
//        String enteringCode = guildService.getEnteringCode(1L);
//        User user = userRepository.findById(2L).orElseThrow();
//        Long guildPosition = 3L;
//        // 초대 코드 기간이 유효하면 guildId, 유효하지 않으면 -1 반환
//        Long guildId = EnteringCodeUtil.isCodeValid(enteringCode);
//        Assertions.assertNotEquals(guildId, -1);
//        // 그룹 없으면 가입 불가
//        Guild guild = guildValidator.isExist(guildId).orElse(null);
//        Assertions.assertNotNull(guild);
//        // 이미 가입되어 있으면 가입 불가
//        Assertions.assertFalse(mapValidator.isAlreadyJoined(user.getId(), guildId));
//        // 그룹 인원 수가 6명보다 많으면 가입 불가
//        Assertions.assertTrue(mapValidator.isUnder6(guildId));
//        // 위치가 유효하지 않으면 가입 불가
//        Assertions.assertTrue(mapValidator.isAblePosition(user.getId(), guildPosition));
//        // 조건 다 만족하면 가입
//        Map map = mapRepository.save(Map.builder().
//                user(user).
//                guild(guild).
//                guildPosition(guildPosition).
//                build());
//        Assertions.assertNotNull(map);
//    }
//}
