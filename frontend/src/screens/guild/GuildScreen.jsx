import React, { useState } from 'react';
import { StyleSheet, View, TouchableOpacity, Image } from 'react-native';
import GuildQuestModal from './GuildQuestModal';
import GuildInviteModal from './GuildInviteModal';
import GuildByeModal from './GuildByeModal';
import CustomModal from '@components/CustomModal/CustomModal';
import CustomText from '@components/CustomText/CustomText';
import CustomButton from '@components/CustomButton/CustomButton';
import { petIdImages } from '@constants/petImage';

import {
  useGuildInfo,
  useMemberInfo,
  useQuestInfo,
  useQuests,
  useByeGuild,
  useChooseQuest,
  useMyInfo,
} from '@hooks/queries/useGuild';

function GuildScreen({ navigation, route }) {
  const { guildId } = route.params;
  const [activeModal, setActiveModal] = useState(null);

  const { data: guildInfo } = useGuildInfo(guildId);
  const { data: memberInfo } = useMemberInfo(guildId);
  const { data: questInfo } = useQuestInfo(guildId);
  const { data: myInfo } = useMyInfo();
  const { mutate: byeGuild } = useByeGuild(guildId);
  console.log('길드정보', guildInfo);
  console.log('멤버', memberInfo);
  console.log('퀘스트', questInfo);
  console.log('내정보', myInfo);

  const guildName = guildInfo?.guildName;
  const { mutate: chooseQuest } = useChooseQuest();
  const members = Array(6)
    .fill(null)
    .map((_, index) => (memberInfo && memberInfo[index]) || null);

  const { data: quests } = useQuests();

  const openModal = (modal) => {
    const isGuildLeader = guildInfo?.guildLeaderId === myInfo?.userId;

    if (modal === 'quest' || modal === 'invite') {
      if (isGuildLeader) {
        setActiveModal(modal);
      } else {
        setActiveModal('leader');
      }
    } else {
      setActiveModal(modal);
    }
  };
  const closeModal = () => setActiveModal(null);

  const refetchQuest = (newQuest) => {
    chooseQuest({ guildId, questId: newQuest.id });
  };

  const [byeError, setByeError] = useState(null);
  const leaveGuild = (guildId) => {
    byeGuild(guildId, {
      onSuccess: () => {
        setByeError(null);
        navigation.navigate('Map');
      },
      onError: (error) => {
        console.log(error.code, error.message);
        if (error.response?.status === 406) {
          console.log('에러걸림');
          setByeError('leader');
        }
      },
    });
  };

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
        <CustomText>
          {questInfo?.guildQuestContent || '일일 퀘스트를 설정해주세요'}
        </CustomText>
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
                  source={petIdImages[member.petId]}
                  style={styles.petImage}
                />
              ) : (
                <CustomText style={styles.placeholderImageText}>+</CustomText>
              )}
            </TouchableOpacity>
            <CustomText style={styles.memberName}>
              {member ? member.userName : '???'}
              {member?.questStatus ? ' ✔️' : ''}
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
      <CustomModal isVisible={activeModal === 'leader'} title={'길드장의 권리'}>
        <CustomText style={styles.leaderModalBody}>
          아아 이건 길드장의 권리다
        </CustomText>
        <CustomButton title={'그래'} onPress={closeModal} style={[]} />
      </CustomModal>

      <GuildQuestModal
        isVisible={activeModal === 'quest'}
        onClose={closeModal}
        onSetQuest={(newQuest) => {
          console.log(newQuest);
          refetchQuest(newQuest);
          closeModal();
        }}
        quests={quests || []}
      />
      <GuildInviteModal
        guildId={guildId}
        isVisible={activeModal === 'invite'}
        onClose={closeModal}
      />
      <GuildByeModal
        guildName={guildName}
        isVisible={activeModal === 'leave'}
        byeError={byeError}
        onClose={() => {
          closeModal();
          setByeError(null);
        }}
        onLeave={() => {
          leaveGuild(guildId);
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
  petImage: {
    marginTop: 15,
    width: 80,
    height: 80,
  },
  memberName: {
    fontSize: 12,
  },
  placeholderImageText: { fontSize: 65, color: '#aaa', fontFamily: '' },
  placeholderText: {
    fontSize: 24,
    color: '#aaa',
  },
  leaderModalBody: {
    alignItems: 'center',
    marginBottom: 20,
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
