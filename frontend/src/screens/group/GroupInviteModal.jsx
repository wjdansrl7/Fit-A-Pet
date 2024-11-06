import React from 'react';
import { View, StyleSheet } from 'react-native';
import CustomModal from '@components/CustomModal/CustomModal';
import CustomButton from '@components/CustomButton/CustomButton';
import CustomText from '@components/CustomText/CustomText';

const GroupInviteModal = ({ inviteCode, isVisible, onClose }) => {
  return (
    <CustomModal
      title="친구 초대하기"
      isVisible={isVisible}
      wantClose={true}
      onClose={onClose}
    >
      <View style={styles.groupModalBody}>
        <CustomText>초대 코드</CustomText>
        <CustomText>{inviteCode}</CustomText>
      </View>
      <CustomButton title="그래" onPress={onClose} />
    </CustomModal>
  );
};

const styles = StyleSheet.create({
  groupModalBody: {},
  groupModalBottom: {
    marginTop: 20,
    width: 250,

    paddingHorizontal: 20,
  },
});

export default GroupInviteModal;
