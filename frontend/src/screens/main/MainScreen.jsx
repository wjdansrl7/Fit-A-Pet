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
  ImageBackground,
} from 'react-native';

import MenuButton from './MenuButton';
import AlbumIcon from '@assets/icons/도감_icon.png';
import MapIcon from '@assets/icons/지도_icon.png';
import MyInfoIcon from '@assets/icons/나의기록_icon.png';
import QuestIcon from '@assets/icons/퀘스트_icon.png';
import FoodLensIcon from '@assets/icons/식단기록_icon.png';

import CustomText from '@components/CustomText/CustomText';
import CustomModal from '@components/CustomModal/CustomModal';

import { colors } from '@constants/colors';
import { petImages, petSpriteImages } from '@constants/petImage';
import { useMainPetInfo, useUpdateNickname } from '@hooks/queries/usePet';

import AnimatedSprite from '@components/AnimatedSprite/AnimatedSprite';
import { fetchHealthData } from '@api/healthData';
import useHealthDataStore from '@src/stores/healthDataStore';
import MainEggModal from './MainEggModal';
import useEggModalDataStore from '@src/stores/eggModalDataStore';
function MainScreen({ navigation }) {
  const [isModalVisible, setModalVisible] = useState(false);
  const [petNickname, setPetNickname] = useState('');
  const [petBookId, setPetBookId] = useState('');
  const { data: mainPetInfo, isLoading, isError, error } = useMainPetInfo();
  const { mutate } = useUpdateNickname();
  const { steps, sleepHours, updateHealthData } = useHealthDataStore();

  const { shouldShowModal, newPetType, newPetStatus, setEggModalData } =
    useEggModalDataStore();
  const [isEggModalVisible, setEggModalVisible] = useState(false); // 에그모달 상태
  const [step, setStep] = useState(1); // 에그모달의 단계 관리

  useEffect(() => {
    // 이후 params 변경 감지
    if (shouldShowModal) {
      console.log('shouldShowModal is true');
      setEggModalVisible(true);
    } else {
      setEggModalVisible(false);
    }
  }, [shouldShowModal]);

  // 메인펫 정보가 바뀌면 업데이트
  useEffect(() => {
    if (mainPetInfo) {
      setPetNickname(mainPetInfo.petNickname);
      setPetBookId(mainPetInfo.petBookId);
    }
  }, [mainPetInfo]);

  // 헬스 데이터 업데이트
  useEffect(() => {
    const initializeHealthData = async () => {
      const { steps, sleepHours } = await fetchHealthData();
      updateHealthData(steps, sleepHours);
    };

    initializeHealthData();
  }, []);

  // 닉네임 변경 함수
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

  // const petImage =
  //   // 대표 이미지 로딩
  //   petImages[mainPetInfo.petType]?.[mainPetInfo.petStatus] || null;

  const petSpriteImage =
    petSpriteImages[mainPetInfo?.petType]?.[mainPetInfo?.petStatus] || null;

  const petSpriteData = require('@assets/pets/sprite/sprite.json');

  // 낮 시간인지 확인
  const isDayTime = 6 <= new Date().getHours() < 18;

  // 밤 시간 테스트용
  // const isDayTime = false;

  const backgroundImageSource = isDayTime
    ? require('@assets/backgrounds/main/sky_day.png')
    : require('@assets/backgrounds/main/sky_night.png');

  // 스프라이트 관련 코드
  const frames = Object.values(petSpriteData.frames).map((frame) => ({
    x: frame.frame.x,
    y: frame.frame.y,
    w: frame.frame.w,
    h: frame.frame.h,
    duration: frame.duration,
  }));
  const animations = {
    walk: [0, 1, 0],
    // 적당히 조절 해야할듯, 아님 동물마다 저장을 하던가
  };

  // 펫북아이디 아니라 회원가입 및 주는 팻 id 값으로 하기
  const handleEggUpdateNickname = async () => {
    if (petNickname.trim()) {
      try {
        mutate({ petBookId, newNickname: petNickname.trim() });
        setStep(3); // Step3로 이동
      } catch (error) {
        console.error('닉네임 업데이트 실패:', error);
      }
    } else {
      Alert.alert('Error', '닉네임을 입력해주세요!');
    }
  };

  const handleEggModalClose = async () => {
    setEggModalVisible(false);

    // Zustand 상태 업데이트
    setEggModalData({ shouldShowModal: false });

    setTimeout(() => {
      setStep(1);
    }, 300); // 300ms 딜레이
  };

  return (
    <View style={styles.container}>
      <ImageBackground
        source={backgroundImageSource}
        style={styles.backgroundImage}
        resizeMode="cover"
      >
        {/* 닉네임은 새걸 받아온걸로 하기 */}
        <MainEggModal
          isVisible={isEggModalVisible}
          step={step}
          petNickname={petNickname}
          setPetNickname={setPetNickname}
          onUpdateNickname={handleEggUpdateNickname}
          onClose={handleEggModalClose}
          setStep={setStep}
          // newPetType={newPetType} // 추가
          // newPetStatus={newPetStatus}
        />

        <Text>{steps}</Text>
        <Text>{sleepHours}</Text>
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

        {/* 우측 메뉴 */}
        <View style={styles.rightMenu}>
          {/* 푸드렌즈 카메라 */}
          <MenuButton
            title={'식단기록'}
            icon={FoodLensIcon}
            isBlack={isDayTime}
          ></MenuButton>
          {/* 퀘스트 모아보기 페이지로 이동 */}

          <Pressable onPress={() => navigation.navigate('Quest')}>
            <MenuButton
              title={'퀘스트'}
              icon={QuestIcon}
              isBlack={isDayTime}
            ></MenuButton>
          </Pressable>
        </View>

        {/* 중앙 - 펫 */}
        <View style={styles.petContainer}>
          {/* 스프라이트 애니메이션으로 대체 */}
          <AnimatedSprite
            source={petSpriteImage} // 소스
            spriteSheetWidth={256} // 실제 스프라이트 크기
            spriteSheetHeight={512}
            width={300} // 프레임의 너비
            height={300} // 프레임의 높이
            frames={frames} // 이건 거의 디폴트임
            animations={animations} // 이걸로 움직이는 화면 조절
            defaultAnimationName="walk" // 이건 해놔야 연동됨
            inLoop={true} // 루프
            autoPlay={true} // 자동시작
            frameRate={3} // 움직이는 속도
          />
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
      </ImageBackground>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    // backgroundColor: '#C1EFFF', // 배경 하늘색
  },
  backgroundImage: {
    flex: 1,
    padding: 20,
  },
  backgroundImageGround: {
    padding: 20,
    position: 'absolute',
    bottom: 0,
    left: 0,
    right: 0,
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
    bottom: 180,
    left: 40,
    right: 40,
    // backgroundColor: 'rgba(0, 0, 0, 0.2)',
  },
  petImage: {
    width: '100%',
    height: '100%',
    backgroundColor: 'tomato',
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
