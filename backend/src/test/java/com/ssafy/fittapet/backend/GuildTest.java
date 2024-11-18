//package com.ssafy.fittapet.backend;
//
//import com.ssafy.fittapet.backend.application.service.guild.GuildService;
//import com.ssafy.fittapet.backend.application.service.map.MapService;
//import com.ssafy.fittapet.backend.application.service.quest.QuestService;
//import com.ssafy.fittapet.backend.common.exception.CustomException;
//import com.ssafy.fittapet.backend.common.util.EnteringCodeUtil;
//import com.ssafy.fittapet.backend.common.validator.GuildValidator;
//import com.ssafy.fittapet.backend.common.validator.MapValidator;
//import com.ssafy.fittapet.backend.domain.dto.guild.GuildMemberInfoResponse;
//import com.ssafy.fittapet.backend.domain.dto.guild.GuildRequest;
//import com.ssafy.fittapet.backend.domain.dto.map.MapResponse;
//import com.ssafy.fittapet.backend.domain.entity.*;
//import com.ssafy.fittapet.backend.domain.repository.Map.MapRepository;
//import com.ssafy.fittapet.backend.domain.repository.UserRepository;
//import com.ssafy.fittapet.backend.domain.repository.guild.GuildRepository;
//import com.ssafy.fittapet.backend.domain.repository.guild_quest.GuildQuestRepository;
//import com.ssafy.fittapet.backend.domain.repository.quest.QuestRepository;
//import com.ssafy.fittapet.backend.domain.repository.user_quest.UserQuestStatusRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//import static com.ssafy.fittapet.backend.common.constant.error_code.GuildErrorCode.ALREADY_SET_QUEST;
//
//@SpringBootTest
//@Transactional
//public class GuildTest {
//
//    @Autowired
//    private GuildService guildService;
//    @Autowired
//    private MapService mapService;
//    @Autowired
//    private QuestService questService;
//
//    @Autowired
//    private MapRepository mapRepository;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private GuildRepository guildRepository;
//    @Autowired
//    private GuildQuestRepository guildQuestRepository;
//    @Autowired
//    private UserQuestStatusRepository userQuestStatusRepository;
//
//    @Autowired
//    private MapValidator mapValidator;
//    @Autowired
//    private GuildValidator guildValidator;
//    @Autowired
//    private QuestRepository questRepository;
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
//    @DisplayName("길드 가입")
//    @Rollback(true)
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
//
//    @Test
//    @DisplayName("맵 정보 갖고오기")
//    @Rollback(true)
//    public void testGetAll(){
//        List<MapResponse> map = mapService.getAll();
//
//        Assertions.assertNotEquals(map.size(), 0);
//    }
//
//    @Test
//    @DisplayName("길드 정보 갖고오기")
//    @Rollback(true)
//    public void testGetGuildInfo(){
//        Long guildId = 1L;
//        Assertions.assertNotNull(guildValidator.isExist(guildId));
//        Assertions.assertNotNull(guildRepository.findInfoById(guildId));
//    }
//
//    @Test
//    @DisplayName("길드 나가기")
//    @Rollback(true)
//    public void testLeaveGuild(){
//        User user = userRepository.findById(2L).orElseThrow();
//        Long guildId = 1L;
//
//        Assertions.assertTrue(mapValidator.isAlreadyJoined(user.getId(), guildId));
//        Assertions.assertNotNull(guildValidator.isExist(guildId));
//
//        mapRepository.deleteByGuildIdAndUserId(guildId, user.getId());
//
//        // userQuestStatus에서 삭제
//        GuildQuest guildQuest = guildQuestRepository.findByGuildId(guildId);
//        if(guildQuest == null) return;
//        userQuestStatusRepository.deleteByUserIdAndGuildQuestId(user.getId(), guildQuest.getId());
//    }
//
//    @Test
//    @DisplayName("길드원 정보 갖고오기")
//    @Rollback(true)
//    public void testGetMemberInfo(){
//        Long guildId = 1L;
//
//        Guild guild = guildValidator.isExist(guildId).orElseThrow();
//
//        List<GuildMemberInfoResponse> list=guildRepository.findAllMemberByGuild(guild.getId());
//        Assertions.assertNotEquals(list.size(), 0);
//    }
//
//    @Test
//    @DisplayName("길드용 퀘스트 검색하기")
//    @Rollback(true)
//    public void testSearchGuildQuest() throws CustomException {
//        Assertions.assertEquals(questService.searchGuildQuest(null).size(), 9);
//        Assertions.assertEquals(questService.searchGuildQuest("SLEEP").size(), 3);
//        Assertions.assertEquals(questService.searchGuildQuest("WALK").size(), 3);
//        Assertions.assertEquals(questService.searchGuildQuest("DIET").size(), 3);
//    }
//
//    @Test
//    @DisplayName("길드 퀘스트 설정하기")
//    @Rollback(true)
//    public void testUpdateGuildQuest() throws CustomException {
//        Long guildId = 1L;
//        Guild guild = guildRepository.findById(1L).orElse(null);
//        Long questId = 1L;
//        Quest quest = questRepository.findById(1L).orElse(null);
//
//        GuildQuest guildQuest = guildQuestRepository.findByGuildId(guildId);
//        if(guildQuest == null){
//            guildQuest = guildQuestRepository.save(GuildQuest.builder().
//                    guild(guild).
//                    quest(quest).
//                    build());
//        }
//        else{
//            if(guildQuest.getQuest().getId().equals(questId)) throw new CustomException(ALREADY_SET_QUEST);
//            guildQuest.setQuest(quest);
//            guildQuestRepository.save(guildQuest);
//        }
//
//        Assertions.assertEquals(guildQuest.getQuest().getId(), questId);
//    }
//}
