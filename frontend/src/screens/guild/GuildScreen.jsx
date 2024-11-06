import React, { useState } from 'react';
import {
  StyleSheet,
  View,
  TouchableOpacity,
  Image,
  Modal,
  Text,
} from 'react-native';
import TreasureBox from '@assets/backgrounds/guild/TreasureBox.png';
import GuildQuestModal from './GuildQuestModal';
import GuildInviteModal from './GuildInviteModal';
import GuildByeModal from './GuildByeModal';

import CustomText from '@components/CustomText/CustomText';
import CustomButton from '@components/CustomButton/CustomButton';

function GuildScreen({ navigation }) {
  const [quest, setQuest] = useState(''); // quest로 변수명 통일
  const [isQuestModalVisible, setQuestModalVisible] = useState(false); // goal을 quest로 변경
  const [isInviteModalVisible, setInviteModalVisible] = useState(false);
  const [isLeaveModalVisible, setLeaveModalVisible] = useState(false);
  const members = Array(6).fill(null);
  const guildName = '문기의 보금자리';
  const inviteCode = '12312123';

  const quests = [
    {
      questId: 3,
      questCategory: 'DIET',
      questName: '삼시세끼 탄단지 섭취',
      questContent: '하루 세끼 모두 탄단지 섭취하기',
      questTier: 'HARD',
      questReward: '경험치 100, 공적치 100',
    },
    {
      questId: 4,
      questCategory: 'SLEEP',
      questName: '내일은 주말!',
      questContent: '수면시간 9시간 채우기',
      questTier: 'NORMAL',
      questReward: '경험치 200, 공적치 100',
    },
    {
      questId: 5,
      questCategory: 'WALK',
      questName: '지옥의 행군..?',
      questContent: '15,000보 걷기',
      questTier: 'EASY',
      questReward: '경험치 150, 공적치 100',
    },
    {
      questId: 6,
      questCategory: 'WALK',
      questName: '지옥의 행군..?',
      questContent: '15,000보 걷기',
      questTier: 'EASY',
      questReward: '경험치 150, 공적치 100',
    },
    {
      questId: 7,
      questCategory: 'WALK',
      questName: '지옥의 행군..?',
      questContent: '15,000보 걷기',
      questTier: 'EASY',
      questReward: '경험치 150, 공적치 100',
    },
    {
      questId: 8,
      questCategory: 'WALK',
      questName: '지옥의 행군..?',
      questContent: '15,000보 걷기',
      questTier: 'EASY',
      questReward: '경험치 150, 공적치 100',
    },
    {
      questId: 9,
      questCategory: 'WALK',
      questName: '지옥의 행군..?',
      questContent: '15,000보 걷기',
      questTier: 'EASY',
      questReward: '경험치 150, 공적치 100',
    },
    {
      questId: 10,
      questCategory: 'WALK',
      questName: '지옥의 행군..?',
      questContent: '15,000보 걷기',
      questTier: 'EASY',
      questReward: '경험치 150, 공적치 100',
    },
  ];
  return (
    <View style={styles.container}>
      {/* 진행 상태 바 */}
      <View style={styles.progressContainer}>
        <View style={styles.progressBar}>
          <View style={styles.progressFill} />
        </View>
        <Image source={TreasureBox} style={styles.treasureBox} />
      </View>

      {/* 퀘스트 설정 영역 */}
      <TouchableOpacity
        activeOpacity={0.8}
        style={styles.questContainer}
        onPress={() => setQuestModalVisible(true)}
      >
        <CustomText>{quest ? quest : '일일 퀘스트를 설정해주세요'}</CustomText>
      </TouchableOpacity>

      {/* 멤버 리스트 */}
      <View style={styles.memberContainer}>
        {members.map((member, index) => (
          <View key={index} style={styles.memberSlot}>
            {member ? (
              <>
                <TouchableOpacity
                  activeOpacity={0.8}
                  style={styles.imageContainer}
                  onPress={() => !member && setInviteModalVisible(true)}
                >
                  <Image
                    source={{ uri: member.image }}
                    style={styles.memberImage}
                  />
                </TouchableOpacity>
                <CustomText style={styles.memberName}>{member.name}</CustomText>
              </>
            ) : (
              <>
                <TouchableOpacity
                  activeOpacity={0.8}
                  style={styles.imageContainer}
                  onPress={() => !member && setInviteModalVisible(true)}
                >
                  <CustomText style={styles.placeholderImageText}>+</CustomText>
                </TouchableOpacity>
                <CustomText style={styles.placeholderText}>???</CustomText>
              </>
            )}
          </View>
        ))}
      </View>

      {/* 그룹 탈퇴 버튼 */}
      <CustomButton
        activeOpacity={0.8}
        style={styles.byeContainer}
        onPress={() => setLeaveModalVisible(true)}
        title="그룹 탈퇴하기"
      />

      {/* 모달 컴포넌트 */}
      <GuildQuestModal
        isVisible={isQuestModalVisible}
        onClose={() => setQuestModalVisible(false)}
        onSetQuest={(newQuest) => {
          setQuest(newQuest);
          setQuestModalVisible(false);
        }}
        quests={quests}
      />
      <GuildInviteModal
        inviteCode={inviteCode}
        isVisible={isInviteModalVisible}
        onClose={() => setInviteModalVisible(false)}
      />
      <GuildByeModal
        guildName={guildName}
        isVisible={isLeaveModalVisible}
        onClose={() => setLeaveModalVisible(false)}
        onLeave={() => {
          // 탈퇴 로직 실행
          setLeaveModalVisible(false);
        }}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    backgroundColor: 'white',
  },
  progressContainer: {
    marginTop: 30,
    flexDirection: 'row',
    alignItems: 'center',
  },
  progressBar: {
    width: 300,
    height: 30,
    backgroundColor: '#eee',
    borderRadius: 20,
  },
  progressFill: {
    width: '100%',
    height: '100%',
    backgroundColor: 'red',
    borderRadius: 20,
  },
  treasureBox: {
    position: 'absolute',
    top: -55,
    right: -10,
    width: 100,
    height: 100,
  },
  questContainer: {
    marginTop: 50,
    marginHorizontal: 10,
    alignItems: 'center',
    padding: 10,
    borderRadius: 20,
    borderWidth: 2,
    borderColor: 'transparent',
    backgroundColor: '#eee',
  },
  memberContainer: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    justifyContent: 'space-between',
    padding: 20,
    marginTop: 20,
  },
  memberSlot: {
    width: '50%',
    alignItems: 'center',
    marginVertical: 10,
  },
  imageContainer: {
    backgroundColor: '#eee',
    width: 100,
    height: 100,
    borderRadius: 100,
    marginBottom: 5,
    paddingBottom: 30,
    alignItems: 'center',
    justifyContent: 'center',
  },
  memberImage: {
    width: 50,
    height: 50,
    borderRadius: 25,
    marginBottom: 5,
  },
  memberName: {
    fontSize: 12,
  },
  placeholderImageText: { fontSize: 65, color: '#aaa', fontFamily: '' },
  placeholderText: {
    fontSize: 24,
    color: '#aaa',
  },
  byeContainer: {
    marginTop: 30,
    marginHorizontal: 50,
    alignItems: 'center',
    padding: 5,
    backgroundColor: 'red',
  },
  modalContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: 'rgba(0, 0, 0, 0.5)',
  },
});

export default GuildScreen;
