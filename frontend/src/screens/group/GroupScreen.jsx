import React, { useState } from 'react';
import {
  StyleSheet,
  View,
  TouchableOpacity,
  Image,
  Modal,
  Text,
} from 'react-native';
import TreasureBox from '@assets/backgrounds/group/TreasureBox.png';
import GroupGoalModal from './GroupGoalModal';
import GroupInviteModal from './GroupInviteModal';
import GroupByeModal from './GroupByeModal';

import CustomText from '@components/CustomText/CustomText';
import CustomButton from '@components/CustomButton/CustomButton';

function GroupScreen({ navigation }) {
  const [goal, setGoal] = useState('하루 20000보 걷기');
  const [isGoalModalVisible, setGoalModalVisible] = useState(false);
  const [isInviteModalVisible, setInviteModalVisible] = useState(false);
  const [isLeaveModalVisible, setLeaveModalVisible] = useState(false);
  const members = Array(6).fill(null); // 예시 멤버 리스트 (빈 슬롯 포함)
  const groupName = '문기의 보금자리';
  const inviteCode = '12312123';
  return (
    <View style={styles.container}>
      {/* 진행 상태 바 */}
      <View style={styles.progressContainer}>
        <View style={styles.progressBar}>
          <View style={styles.progressFill} />
        </View>
        <Image source={TreasureBox} style={styles.treasureBox} />
      </View>

      {/* 목표 설정 영역 */}
      <TouchableOpacity
        activeOpacity={0.8}
        style={styles.goalContainer}
        onPress={() => setGoalModalVisible(true)}
      >
        <CustomText>{goal ? goal : '목표를 설정해주세요!'}</CustomText>
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
      <GroupGoalModal
        isVisible={isGoalModalVisible}
        onClose={() => setGoalModalVisible(false)}
        onSetGoal={() => setGoalModalVisible(false)}
      />
      <GroupInviteModal
        inviteCode={inviteCode}
        isVisible={isInviteModalVisible}
        onClose={() => setInviteModalVisible(false)}
      />
      <GroupByeModal
        groupName={groupName}
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
    width: '100%', // 진행 상태 (예: 30%로 가정)
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
  goalContainer: {
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
    width: '50%', // 3열로 배치하기 위해 너비를 33.3%로 설정
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
    backgroundColor: 'rgba(0, 0, 0, 0.5)', // 반투명 배경
  },
});

export default GroupScreen;
