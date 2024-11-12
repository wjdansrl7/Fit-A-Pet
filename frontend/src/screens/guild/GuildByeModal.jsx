import React from 'react';
import { View, StyleSheet } from 'react-native';
import CustomModal from '@components/CustomModal/CustomModal';
import CustomButton from '@components/CustomButton/CustomButton';
import CustomText from '@components/CustomText/CustomText';

const GuildByeModal = ({ guildName, isVisible, onClose, onLeave }) => {
  return (
    <CustomModal title="그룹 나가기?" isVisible={isVisible} onClose={onClose}>
      <View style={styles.guildModalBody}>
        <CustomText>{guildName}에서</CustomText>
        <CustomText>정말로 나가실 건가요?</CustomText>
      </View>
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
});

export default GuildByeModal;
