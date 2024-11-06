import React, { useState } from 'react';
import { View, StyleSheet, TextInput } from 'react-native';
import CustomModal from '@components/CustomModal/CustomModal';
import CustomText from '@components/CustomText/CustomText';
import CustomButton from '@components/CustomButton/CustomButton';

const MapModal = ({
  isVisible,
  viewState,
  onClose,
  onCreateGroup,
  onJoinGroup,
}) => {
  const [errorState, setErrorState] = useState(null); // 에러 상태: 'duplicate', 'invalidCode', 'full'
  const [newGroupValue, setNewGroupValue] = useState('');
  const [inviteCodeValue, setInviteCodeValue] = useState('');

  const handleCreateGroup = () => {
    const groupExists = true; // 예시 로직
    if (groupExists) {
      setErrorState('duplicate');
    } else {
      onCreateGroup();
      setErrorState(null); // 성공 시 에러 상태 초기화
    }
    setNewGroupValue(''); // 입력 값 초기화
  };

  const handleJoinGroup = () => {
    const isCodeValid = true; // 예시 로직
    const isGroupFull = true; // 예시 로직
    if (!isCodeValid) {
      setErrorState('invalidCode');
    } else if (isGroupFull) {
      console.log('꽉참');
      setErrorState('full');
    } else {
      onJoinGroup();
      setErrorState(null); // 성공 시 에러 상태 초기화
    }
    setInviteCodeValue(''); // 입력 값 초기화
  };

  const goBack = () => {
    setErrorState(null);
  };

  return (
    <CustomModal
      isVisible={isVisible}
      wantClose={true}
      title={
        errorState === 'duplicate'
          ? '중복된 이름!'
          : errorState === 'invalidCode'
          ? '코드 불일치!'
          : errorState === 'full'
          ? '그룹 인원 제한!'
          : viewState === 'init'
          ? '빈 그룹이네요!'
          : viewState === 'create'
          ? '그룹을 만들어봐요!'
          : '그룹에 들어가요!'
      }
      onClose={() => {
        onClose();
        setErrorState(null); // 모달 닫을 때 에러 상태 초기화
      }}
    >
      {viewState === 'init' && (
        <>
          <View style={styles.mapModalBody}>
            <CustomText>아래 버튼 중 하나를 눌러</CustomText>
            <CustomText style={{ marginBottom: 10 }}>
              그룹을 만들거나 들어가요!
            </CustomText>

            <CustomText style={styles.alertText}>
              * 들어가기는 친구가 보내준 그룹
            </CustomText>
            <CustomText style={styles.alertText}>
              초대코드가 필요해요!
            </CustomText>
          </View>
          <View style={styles.mapModalBottomTwoB}>
            <CustomButton
              title="만들기"
              onPress={() => setErrorState(null) || onCreateGroup()}
            />
            <CustomButton
              title="들어가기"
              onPress={() => setErrorState(null) || onJoinGroup()}
            />
          </View>
        </>
      )}

      {viewState === 'create' && (
        <>
          <View style={styles.mapModalBody}>
            {errorState === 'duplicate' ? (
              <>
                <CustomText style={styles.errorText}>
                  이미 있는 그룹 이름이네요..
                </CustomText>
                <CustomText style={styles.errorText}>
                  다시 입력해주세요!
                </CustomText>

                <View style={styles.mapModalBottom}>
                  <CustomButton title="알겠어" onPress={goBack} />
                </View>
              </>
            ) : (
              <>
                <CustomText>
                  새로운 그룹을 생성을 위해 그룹 이름을 지어주세요!
                </CustomText>
                <TextInput
                  style={styles.input}
                  placeholder="그룹 이름은?"
                  value={newGroupValue}
                  onChangeText={setNewGroupValue}
                  keyboardType="default"
                />
                <View style={styles.mapModalBottom}>
                  <CustomButton title="이거야" onPress={handleCreateGroup} />
                </View>
              </>
            )}
          </View>
        </>
      )}

      {viewState === 'join' && (
        <>
          <View style={styles.mapModalBody}>
            {errorState === 'invalidCode' ? (
              <>
                <CustomText style={styles.errorText}>
                  초대 코드가 맞지 않아요!
                </CustomText>
                <CustomText style={styles.errorText}>
                  다시 코드를 입력해봐요!
                </CustomText>
                <View style={styles.mapModalBottom}>
                  <CustomButton title="알겠어" onPress={goBack} />
                </View>
              </>
            ) : errorState === 'full' ? (
              <>
                <CustomText style={styles.errorText}>
                  해당 그룹은 이미 6명이라
                </CustomText>
                <CustomText style={styles.errorText}>못들어가요</CustomText>
                <CustomText style={styles.errorText}>
                  다시 코드를 입력해봐요!
                </CustomText>

                <View style={styles.mapModalBottom}>
                  <CustomButton title="알겠어" onPress={goBack} />
                </View>
              </>
            ) : (
              <>
                <CustomText>선택한 그룹에 참여하려면</CustomText>
                <CustomText>초대 코드를 입력해주세요!</CustomText>
                <TextInput
                  style={styles.input}
                  placeholder="초대 코드는.."
                  value={inviteCodeValue}
                  onChangeText={setInviteCodeValue}
                  keyboardType="default"
                />
                <View style={styles.mapModalBottom}>
                  <CustomButton title="이거야" onPress={handleJoinGroup} />
                </View>
              </>
            )}
          </View>
        </>
      )}
    </CustomModal>
  );
};

const styles = StyleSheet.create({
  mapModalBody: {
    width: 280,
  },
  mapModalBottom: {
    marginTop: 10,
    width: 300,
    flexDirection: 'row',
    justifyContent: 'center',
    paddingHorizontal: 20,
  },
  mapModalBottomTwoB: {
    marginTop: 20,
    width: 300,
    flexDirection: 'row',
    justifyContent: 'space-between',
    paddingHorizontal: 20,
  },
  alertText: {
    fontSize: 16,
  },
  errorText: {
    color: 'red', // 에러 메시지 스타일
  },
  input: {
    fontFamily: 'DungGeunMo',
    fontSize: 16,
    backgroundColor: 'gray', // 색 변화시킬거
    paddingLeft: 15,
    borderRadius: 20,
    borderWidth: 2,
    borderColor: 'transparent',
    marginTop: 10,
  },
});

export default MapModal;
