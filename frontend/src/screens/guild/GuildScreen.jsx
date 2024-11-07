import React, { useState } from 'react';
import { StyleSheet, View, TouchableOpacity, Image } from 'react-native';
import TreasureBox from '@assets/backgrounds/guild/TreasureBox.png';
import GuildQuestModal from './GuildQuestModal';
import GuildInviteModal from './GuildInviteModal';
import GuildByeModal from './GuildByeModal';

import CustomText from '@components/CustomText/CustomText';
import CustomButton from '@components/CustomButton/CustomButton';

function GuildScreen({ navigation }) {
  const [quest, setQuest] = useState('');
  const [activeModal, setActiveModal] = useState(null);
  const members = Array(6).fill(null);
  const guildName = '문기의 보금자리';
  const inviteCode = '12312123';

  const quests = [
    // quests data here...
  ];

  const openModal = (modal) => setActiveModal(modal);
  const closeModal = () => setActiveModal(null);

  return (
    <View style={styles.container}>
      {/* 추가 기능 */}
      {/* <View style={styles.progressContainer}>
        <View style={styles.progressBar}>
          <View style={styles.progressFill} />
        </View>
        <Image source={TreasureBox} style={styles.treasureBox} />
      </View> */}
      <View style={styles.nameContainer}>
        <CustomText>{guildName}</CustomText>
      </View>

      <TouchableOpacity
        activeOpacity={0.8}
        style={styles.questContainer}
        onPress={() => openModal('quest')}
      >
        <CustomText>{quest || '일일 퀘스트를 설정해주세요'}</CustomText>
      </TouchableOpacity>

      <View style={styles.memberContainer}>
        {members.map((member, index) => (
          <View key={index} style={styles.memberSlot}>
            <TouchableOpacity
              activeOpacity={0.8}
              style={styles.imageContainer}
              onPress={() => !member && openModal('invite')}
            >
              {member ? (
                <Image
                  source={{ uri: member.image }}
                  style={styles.memberImage}
                />
              ) : (
                <CustomText style={styles.placeholderImageText}>+</CustomText>
              )}
            </TouchableOpacity>
            <CustomText style={styles.memberName}>
              {member ? member.name : '???'}
            </CustomText>
          </View>
        ))}
      </View>

      <CustomButton
        activeOpacity={0.8}
        style={styles.byeContainer}
        onPress={() => openModal('leave')}
        title="그룹 탈퇴하기"
      />

      <GuildQuestModal
        isVisible={activeModal === 'quest'}
        onClose={closeModal}
        onSetQuest={(newQuest) => {
          setQuest(newQuest);
          closeModal();
        }}
        quests={quests}
      />
      <GuildInviteModal
        inviteCode={inviteCode}
        isVisible={activeModal === 'invite'}
        onClose={closeModal}
      />
      <GuildByeModal
        guildName={guildName}
        isVisible={activeModal === 'leave'}
        onClose={closeModal}
        onLeave={() => {
          // 탈퇴 로직 실행
          closeModal();
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
  nameContainer: { alignSelf: 'center', marginTop: 10 },

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
    marginTop: 30,
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
