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
  Dimensions,
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
  const { data: mainPetInfo, isLoading, isError, error } = useMainPetInfo();
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
    return <Text>Error occurred: {error.message}</Text>;
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
          <View style={styles.inputContainer}>
            <TextInput
              style={styles.input}
              value={petNickname}
              onChangeText={setPetNickname}
              keyboardType="default"
            />
          </View>

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

      {/* 중앙 - 펫 */}
      <View style={styles.petContainer}>
        {/* <Image source={petImage} style={styles.petImage} /> */}
        <Image
          source={require('@assets/pets/beluga_3.png')}
          style={styles.petImage}
        />
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
    marginBottom: 10,
    marginHorizontal: 20,
    backgroundColor: 'rgba(255, 255, 255, 0.5)',
    opacity: 100,
    borderRadius: 10,
    borderWidth: 2,
    borderColor: 'black',
  },
  petName: {
    fontSize: 26,
    marginBottom: 5,
    width: '80%',
    textAlign: 'center',
  },
  petNameUpdate: {
    position: 'absolute',
    right: 20,
    top: 15,
  },
  levelContainer: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  levelText: {
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
    width: '90%',
    height: 30,
    backgroundColor: '#eee',
    borderRadius: 20,
  },
  progressFill: {
    height: '100%',
    backgroundColor: colors.STAR_YELLOW,
    borderRadius: 20,
  },
  petContainer: {
    alignItems: 'center',
    padding: 10,
    height: Dimensions.get('screen').width - 80,
    position: 'absolute',
    bottom: 150,
    left: 40,
    right: 40,
    // backgroundColor: 'rgba(0, 0, 0, 0.2)',
  },
  petImage: {
    width: '100%',
    height: '100%',
    // backgroundColor: 'tomato',
  },

  rightMenu: {
    paddingHorizontal: 20,
    alignItems: 'flex-end',
  },

  bottomMenu: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    paddingHorizontal: 20,
    position: 'absolute',
    bottom: 20,
    left: 20,
    right: 20,
  },

  inputContainer: {
    width: 250,
    backgroundColor: colors.BACKGROUND_COLOR,
    paddingHorizontal: 15,
    borderRadius: 10,
    alignItems: 'center',
  },

  input: {
    fontFamily: 'DungGeunMo',
    fontSize: 25,
  },

  button: {
    justifyContent: 'center',
    alignItems: 'center',
    borderRadius: 10,
    backgroundColor: colors.MAIN_GREEN,
    padding: 15,
    marginTop: 20,
  },
});

export default MainScreen;
