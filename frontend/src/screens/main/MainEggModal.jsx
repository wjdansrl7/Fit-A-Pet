import React, { useState } from 'react';
import { View, TouchableOpacity, TextInput, StyleSheet } from 'react-native';
import CustomModal from '@components/CustomModal/CustomModal';
import CustomText from '@components/CustomText/CustomText';
import CustomButton from '@components/CustomButton/CustomButton';
import { colors } from '@src/constants';

const MainEggModal = ({
  isVisible,
  step,
  petNickname,
  setPetNickname,
  onUpdateNickname,
  onClose,
  setStep,
  // newPetType,
  // newPetStatus,
}) => {
  return (
    <CustomModal title="알을 받아봐요!" isVisible={isVisible} onClose={onClose}>
      {step === 1 && (
        <>
          <View style={styles.bodyContainer}>
            <CustomText style={styles.bodyText}>알이 왔네요!</CustomText>
            <CustomText>이름을 지어주실래요?</CustomText>
          </View>
          <View style={styles.twoButtonContainer}>
            <CustomButton title="다음에" onPress={() => setStep(3)} />
            <CustomButton title="좋아" onPress={() => setStep(2)} />
          </View>
        </>
      )}

      {step === 2 && (
        <>
          <View style={styles.bodyContainer}>
            <CustomText style={styles.bodyText}>좋아요!</CustomText>
            <CustomText style={styles.bodyText}>이름은 뭐로 할까요?</CustomText>

            <TextInput
              style={styles.input}
              placeholder="내 펫의 이름은..."
              value={petNickname}
              onChangeText={setPetNickname}
            />
          </View>
          <View style={styles.buttonContainer}>
            <CustomButton title="이거야!" onPress={onUpdateNickname} />
          </View>
        </>
      )}

      {step === 3 && (
        <>
          <View style={styles.bodyContainer}>
            <CustomText style={styles.bodyText}>이제 퀘스트를 통해</CustomText>
            <CustomText>당신의 친구를 성장시켜줘요!</CustomText>
          </View>
          <View style={styles.buttonContainer}>
            <CustomButton title="그래!" onPress={onClose} />
          </View>
        </>
      )}
    </CustomModal>
  );
};

const styles = StyleSheet.create({
  bodyContainer: { width: 280, justifyContent: 'center' },
  bodyText: {
    marginBottom: 5,
  },
  buttonContainer: {
    flexDirection: 'row',
    justifyContent: 'space-around',
    marginTop: 20,
  },

  twoButtonContainer: {
    marginTop: 20,
    width: 250,
    flexDirection: 'row',
    justifyContent: 'space-between',
    paddingHorizontal: 20,
  },
  input: {
    fontFamily: 'DungGeunMo',
    fontSize: 16,
    backgroundColor: colors.BACKGROUND_COLOR,
    paddingLeft: 15,
    borderRadius: 20,
  },
});

export default MainEggModal;
