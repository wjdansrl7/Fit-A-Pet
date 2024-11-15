import React from 'react';
import { View, StyleSheet } from 'react-native';
import CustomModal from '@components/CustomModal/CustomModal';
import CustomButton from '@components/CustomButton/CustomButton';
import CustomText from '@components/CustomText/CustomText';
import { colors } from '@constants/colors';
const GuildByeModal = ({
  guildName,
  byeError,
  isVisible,
  onClose,
  onLeave,
}) => {
  return (
    <CustomModal title="그룹 나가기?" isVisible={isVisible} onClose={onClose}>
      <View style={styles.guildModalBody}>
        <CustomText>{guildName}에서</CustomText>
        <CustomText>정말로 나가실 건가요?</CustomText>
      </View>

      {/* error가 null이 아닐 때만 에러 메시지 표시 */}
      {byeError && (
        <CustomText style={styles.errorText}>
          길드장은 나갈 수 없습니다.
        </CustomText>
      )}

      <View style={styles.guildModalBottomTwoB}>
        <CustomButton title="아니" onPress={onClose} />
        <CustomButton title="그래" onPress={onLeave} />
      </View>
    </CustomModal>
  );
};

const styles = StyleSheet.create({
  guildModalBody: {
    marginTop: -5,
  },
  guildModalBottomTwoB: {
    marginTop: 20,
    width: 250,
    flexDirection: 'row',
    justifyContent: 'space-between',
    paddingHorizontal: 20,
  },
  errorText: {
    color: colors.MAIN_ORANGE, // 에러 텍스트 색상 설정
    marginTop: 10, // 필요시 여백 추가
  },
});

export default GuildByeModal;
