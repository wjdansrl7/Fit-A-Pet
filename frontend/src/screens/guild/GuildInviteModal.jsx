import React, { useState, useEffect } from 'react';
import { View, TouchableOpacity, StyleSheet } from 'react-native';
import Clipboard from '@react-native-clipboard/clipboard';
import CustomModal from '@components/CustomModal/CustomModal';
import CustomText from '@components/CustomText/CustomText';
import CustomButton from '@components/CustomButton/CustomButton';
import { colors } from '@src/constants';
import { getEnteringCode } from '@api/guild';
const GuildInviteModal = ({ guildId, isVisible, onClose }) => {
  const [isAlertVisible, setAlertVisible] = useState(false);
  const [inviteCode, setInviteCode] = useState(null);

  useEffect(() => {
    const fetchInviteCode = async () => {
      try {
        const code = await getEnteringCode(guildId);
        setInviteCode(code);
      } catch (error) {
        console.error('Failed to fetch invite code:', error);
      }
    };

    if (isVisible && !inviteCode) {
      fetchInviteCode();
    }
  }, [isVisible, guildId]);

  const handleCopyInviteCode = () => {
    Clipboard.setString(inviteCode);
    setAlertVisible(true);
  };

  const handleCloseInviteCode = () => {
    onClose();
    setAlertVisible(false);
  };

  return (
    <CustomModal title="친구 초대하기" isVisible={isVisible} onClose={onClose}>
      <View style={styles.guildModalBody}>
        <CustomText>초대 코드예요. {'\n'}클릭해서 복사해가세요!</CustomText>

        <TouchableOpacity activeOpacity={0.8} onPress={handleCopyInviteCode}>
          <CustomText style={styles.inviteCodeText}>{inviteCode}</CustomText>
        </TouchableOpacity>
      </View>

      {/* 복사 완료 커스텀 알림 모달 */}
      <CustomModal
        title="복사 완료!"
        isVisible={isAlertVisible}
        onClose={() => setAlertVisible(false)}
      >
        <View style={styles.guildModalBody}>
          <CustomText style={styles.alertText}>
            초대 코드가 복사되었답니다
          </CustomText>
        </View>
        <CustomButton title="확인" onPress={handleCloseInviteCode} />
      </CustomModal>
    </CustomModal>
  );
};

const styles = StyleSheet.create({
  guildModalBody: {
    alignItems: 'center',
    marginBottom: 20,
  },
  inviteCodeText: {
    marginTop: 10,
    fontSize: 20,
    color: colors.MAIN_GREEN,
    textDecorationLine: 'underline',
  },
  guildModalBottom: {
    marginTop: 20,
    paddingHorizontal: 20,
  },
  alertContainer: {
    padding: 20,
    alignItems: 'center',
    marginBottom: 20,
  },
  alertText: {
    fontSize: 20,
    marginVertical: 15,
  },
});

export default GuildInviteModal;
