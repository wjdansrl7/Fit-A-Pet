import React, { useState, useEffect } from 'react';
import {
  View,
  Text,
  StyleSheet,
  Image,
  Pressable,
  TouchableOpacity,
  TextInput,
  ActivityIndicator,
  Alert,
} from 'react-native';

import MenuButton from './MenuButton';
import CustomText from '@components/CustomText/CustomText';
import AlbumIcon from '@assets/icons/도감_icon.png';

import MapIcon from '@assets/icons/지도_icon.png';
import MyInfoIcon from '@assets/icons/나의기록_icon.png';
import QuestIcon from '@assets/icons/퀘스트_icon.png';
import FoodLensIcon from '@assets/icons/식단기록_icon.png';

import CustomModal from '@components/CustomModal/CustomModal';
import { colors } from '@constants/colors';
import { petImages } from '@constants/petImage';
import { useMainPetInfo, useUpdateNickname } from '@hooks/queries/usePet';
// import { useQueryClient } from '@tanstack/react-query';

function MainScreen({ navigation }) {
  const [isModalVisible, setModalVisible] = useState(false);
  const [petNickname, setPetNickname] = useState('');
  const [petBookId, setPetBookId] = useState('');
  const { data: mainPetInfo, isLoading, isError } = useMainPetInfo();
  const { mutate } = useUpdateNickname();

  useEffect(() => {
    if (mainPetInfo) {
      setPetNickname(mainPetInfo.petNickname);
      setPetBookId(mainPetInfo.petBookId);
    }
  }, [mainPetInfo]);

  const handleUpdateNickname = () => {
    if (petNickname.trim()) {
      mutate({ petBookId, newNickname: petNickname.trim() });
      setModalVisible(false);
    } else {
      Alert.alert('Error', '닉네임을 입력해주세요');
    }
  };

  if (isLoading) {
    return <ActivityIndicator size="large" color={colors.MAIN_GREEN} />;
  }
  if (isError) {
    return <Text>Error occurred: {isError.message}</Text>;
  }

  const petImage =
    // 대표 이미지 로딩
    petImages[mainPetInfo.petType]?.[mainPetInfo.petStatus] || null;

  return (
    <View style={styles.container}>
      {/* 상단 - 레벨 및 진행 상태 */}
      <View style={styles.header}>
        <View></View>
        <CustomText style={styles.petName}>
          {mainPetInfo.petNickname}
        </CustomText>

        {/* 펫 닉네임 수정 */}
        <Pressable
          style={styles.petNameUpdate}
          onPress={() => setModalVisible(true)}
        >
          <Image
            source={require('@assets/icons/pencil_icon.png')}
            style={{ width: 30, height: 30 }}
          />
        </Pressable>

        <CustomModal
          isVisible={isModalVisible}
          wantClose={true}
          title="닉네임 수정"
          onClose={
            () => {
              setPetNickname(mainPetInfo.petNickname);
              setModalVisible(false);
            } // 모달을 닫는 함수
          }
        >
          <TextInput
            style={styles.input}
            value={petNickname}
            onChangeText={setPetNickname}
            keyboardType="default"
          />

          <TouchableOpacity
            activeOpacity={0.8}
            style={styles.button}
            onPress={handleUpdateNickname}
          >
            <CustomText style={{ color: 'white' }}>변경하기</CustomText>
          </TouchableOpacity>
        </CustomModal>

        <View style={styles.levelContainer}>
          <CustomText style={styles.levelText}>
            {mainPetInfo.petLevel}
          </CustomText>
          <View style={styles.progressBar}>
            <View
              style={[
                styles.progressFill,
                { width: `${mainPetInfo.petPercent}%` },
              ]}
            />
          </View>
        </View>
      </View>

      {/* 중앙 - 펫(알 모양) */}
      <View style={styles.petContainer}>
        <Image source={petImage} style={styles.petImage} />
      </View>

      {/* 우측 메뉴 */}
      <View style={styles.rightMenu}>
        {/* 푸드렌즈 카메라 */}
        <MenuButton title={'식단기록'} icon={FoodLensIcon}></MenuButton>
        {/* 퀘스트 모아보기 페이지로 이동 */}

        <Pressable onPress={() => navigation.navigate('Quest')}>
          <MenuButton title={'퀘스트'} icon={QuestIcon}></MenuButton>
        </Pressable>
      </View>

      {/* 하단 메뉴 */}
      <View style={styles.bottomMenu}>
        {/* 나머지 페이지 만들어지면 연결 */}

        <Pressable onPress={() => navigation.navigate('Map')}>
          <MenuButton title={'지도'} icon={MapIcon}></MenuButton>
        </Pressable>
        <Pressable onPress={() => navigation.navigate('Album')}>
          <MenuButton title={'도감'} icon={AlbumIcon}></MenuButton>
        </Pressable>
        <Pressable onPress={() => navigation.navigate('MyInfo')}>
          <MenuButton title={'기록보기'} icon={MyInfoIcon}></MenuButton>
        </Pressable>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#C1EFFF', // 배경 하늘색
    padding: 20,
  },
  header: {
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: 15,
    marginTop: 30,
    marginBottom: 20,
    marginHorizontal: 20,
    backgroundColor: 'rgba(255, 255, 255, 0.5)',
    opacity: 100,
    borderRadius: 10,
    borderWidth: 2,
    borderColor: 'black',
  },
  petName: {
    fontSize: 26,
    // fontWeight: 'bold',
    marginBottom: 5,
    width: '80%',
    textAlign: 'center',
  },
  petNameUpdate: {
    backgroundColor: 'yellowgreen',
    position: 'absolute',
    right: 20,
    top: 15,
  },
  levelContainer: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  levelText: {
    // fontWeight: 'bold',
    fontSize: 30,
    backgroundColor: '#00BFFF',
    marginRight: -20,
    width: 50,
    height: 50,
    zIndex: 10,
    borderRadius: 40,
    textAlign: 'center',
    textAlignVertical: 'center',
  },
  progressBar: {
    width: 250,
    height: 30,
    backgroundColor: '#eee',
    borderRadius: 20,
  },
  progressFill: {
    height: '100%',
    backgroundColor: '#FFD700', // 노란색
    borderRadius: 20,
  },
  petContainer: {
    alignItems: 'center',
    padding: 10,
    marginTop: 220,
    marginBottom: 20,
    backgroundColor: 'rgba(0, 0, 0, 0.2)',
  },
  petImage: {
    width: 270,
    height: 270,
  },
  rightMenu: {
    position: 'absolute',
    right: 30,
    top: 200,
  },
  bottomMenu: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    paddingHorizontal: 40,
    position: 'absolute',
    bottom: 10,
    left: 20,
    right: 20,
  },

  input: {
    fontFamily: 'DungGeunMo',
    fontSize: 25,
    // backgroundColor: colors.BACKGROUND_COLOR,
    paddingLeft: 15,
    borderRadius: 20,
  },

  button: {
    justifyContent: 'center',
    alignItems: 'center',
    borderRadius: 10,
    backgroundColor: 'seagreen',
    padding: 15,
    marginTop: 20,
  },
});

export default MainScreen;
