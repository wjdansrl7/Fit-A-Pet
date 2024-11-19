import React, { useState } from 'react';
import { View, StyleSheet, TextInput, TouchableOpacity } from 'react-native';
import CustomModal from '@components/CustomModal/CustomModal';
import CustomText from '@components/CustomText/CustomText';
import CustomButton from '@components/CustomButton/CustomButton';
import { colors } from '@src/constants';

const MapModal = ({
  isVisible,
  viewState,
  errorState,
  setViewState,
  setErrorState,
  selectedHouse,
  onClose,
  onCreateGuild,
  onJoinGuild,
}) => {
  const [newGuildValue, setNewGuildValue] = useState('');
  const [inviteCodeValue, setInviteCodeValue] = useState('');

  const handleCreateGuild = async () => {
    if (!newGuildValue.trim()) {
      return;
    }
    const guildCreateInfo = {
      guildPosition: selectedHouse?.position,
      guildName: newGuildValue,
    };
    onCreateGuild(guildCreateInfo);
    setNewGuildValue('');
  };

  const handleJoinGuild = () => {
    if (!inviteCodeValue.trim()) {
      return;
    }
    const guildJoinInfo = {
      guildPosition: selectedHouse?.position,
      enteringCode: inviteCodeValue,
    };
    onJoinGuild(guildJoinInfo);

    setInviteCodeValue('');
  };

  return (
    <CustomModal
      modalStyle={styles.mapModalContainer}
      isVisible={isVisible}
      wantClose={true}
      title={
        errorState === 'duplicate'
          ? '중복된 이름!'
          : errorState === 'invalidCode'
          ? '코드 불일치!'
          : errorState === 'full'
          ? '길드 인원 제한!'
          : viewState === 'init'
          ? '빈 길드네요!'
          : viewState === 'create'
          ? '길드를 만들어봐요!'
          : '초대코드 입력'
      }
      onClose={() => {
        onClose();
        setErrorState(null);
      }}
    >
      {viewState === 'init' && (
        <>
          <View style={styles.mapModalTwoBtnContainer}>
            <TouchableOpacity
              activeOpacity={0.8}
              style={styles.mapTwoBtn}
              onPress={() => setErrorState(null) || setViewState('create')}
            >
              <CustomText>길드</CustomText>
              <CustomText>생성하기</CustomText>
            </TouchableOpacity>

            <TouchableOpacity
              activeOpacity={0.8}
              style={styles.mapTwoBtn}
              onPress={() => setErrorState(null) || setViewState('join')}
            >
              <CustomText>초대코드</CustomText>
              <CustomText>입력</CustomText>
            </TouchableOpacity>
          </View>
        </>
      )}

      {viewState === 'create' && (
        <>
          <View style={styles.mapModalBody}>
            <TextInput
              style={styles.input}
              placeholder="길드 이름은?"
              value={newGuildValue}
              onChangeText={setNewGuildValue}
              keyboardType="default"
            />
            {errorState === 'duplicate' ? (
              <>
                <CustomText style={styles.errorText}>
                  이미 있는 길드 이름이네요
                </CustomText>
              </>
            ) : (
              <>
                <CustomText></CustomText>
              </>
            )}
            <View style={styles.mapModalBottom}>
              <CustomButton title="이거야" onPress={handleCreateGuild} />
            </View>
          </View>
        </>
      )}

      {viewState === 'join' && (
        <>
          <View style={styles.mapModalBody}>
            <TextInput
              style={styles.input}
              placeholder="초대코드는.."
              value={inviteCodeValue}
              onChangeText={setInviteCodeValue}
              keyboardType="default"
            />
            {errorState === 'invalidCode' ? (
              <>
                <CustomText style={styles.errorText}>
                  잘못된 초대코드예요!
                </CustomText>
              </>
            ) : errorState === 'full' ? (
              <>
                <CustomText style={styles.errorText}>
                  길드 인원이 다 찼어요
                </CustomText>
              </>
            ) : (
              <>
                <CustomText></CustomText>
              </>
            )}
            <View style={styles.mapModalBottom}>
              <CustomButton title="이거야" onPress={handleJoinGuild} />
            </View>
          </View>
        </>
      )}
    </CustomModal>
  );
};

const styles = StyleSheet.create({
  mapModalContainer: {
    height: 200,
    alignItems: 'center',
  },
  mapModalBody: {
    width: 280,
    gap: 5,
    justifyContent: 'center',
  },

  mapModalBottom: {
    paddingHorizontal: 80,
  },
  mapModalTwoBtnContainer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    gap: 20, // 더 적은 간격으로 카드 스타일을 부각
  },
  mapTwoBtn: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    paddingVertical: 20,
    borderRadius: 10,
    borderWidth: 1,
    borderColor: colors.BACKGROUND_COLOR,
    backgroundColor: colors.WHITE,
    elevation: 8,
  },
  errorText: {
    color: colors.MAIN_ORANGE,
  },
  input: {
    fontFamily: 'DungGeunMo',
    fontSize: 16,
    backgroundColor: colors.BACKGROUND_COLOR,
    paddingLeft: 15,
    borderRadius: 20,
  },
});

export default MapModal;

// 주석:
// - onCreateGuild: 그룹 생성 함수 (길드 생성 함수로 변경 필요)
// - onJoinGuild: 그룹 참여 함수 (길드 참여 함수로 변경 필요)
// - handleCreateGuild: 그룹 생성 핸들러 (길드 생성 핸들러로 변경 필요)
// - handleJoinGuild: 그룹 참여 핸들러 (길드 참여 핸들러로 변경 필요)
