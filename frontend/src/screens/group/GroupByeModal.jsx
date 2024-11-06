import React from 'react';
import { View, StyleSheet } from 'react-native';
import CustomModal from '@components/CustomModal/CustomModal';
import CustomButton from '@components/CustomButton/CustomButton';
import CustomText from '@components/CustomText/CustomText';

const GroupByeModal = ({ groupName, isVisible, onClose, onLeave }) => {
  return (
    <CustomModal title="그룹 나가기?" isVisible={isVisible} onClose={onClose}>
      <View style={styles.groupModalBody}>
        <CustomText>{groupName}에서</CustomText>
        <CustomText>정말로 나가실껀가요..?</CustomText>
      </View>
      <View style={styles.groupModalBottomTwoB}>
        <CustomButton title="아니" onPress={onLeave} />
        <CustomButton title="그래" onPress={onClose} />
      </View>
    </CustomModal>
  );
};

const styles = StyleSheet.create({
  groupModalBody: {
    marginTop: -5,
  },
  groupModalBottomTwoB: {
    marginTop: 20,
    width: 250,
    flexDirection: 'row',
    justifyContent: 'space-between',
    paddingHorizontal: 20,
  },
});

export default GroupByeModal;
