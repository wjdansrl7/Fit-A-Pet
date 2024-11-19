import React, { useState, useEffect, useCallback } from 'react';
import {
  View,
  StyleSheet,
  Image,
  Pressable,
  TouchableOpacity,
  TextInput,
  ActivityIndicator,
  Alert,
  NativeModules,
  Dimensions,
  ImageBackground,
} from 'react-native';
import { useFocusEffect } from '@react-navigation/native';
import { launchCamera } from 'react-native-image-picker';

// 네이티브 모듈
const { FoodLensModule } = NativeModules;

// 애셋 관련 임포트
import AlbumIcon from '@assets/icons/album_icon.png';
import MapIcon from '@assets/icons/map_icon.png';
import MyInfoIcon from '@assets/icons/my_info_icon.png';
import QuestIcon from '@assets/icons/quest_icon.png';
import FoodLensIcon from '@assets/icons/food_lens_icon.png';

// 컴포넌트 관련 임포트
import MenuButton from './MenuButton';
import CustomText from '@components/CustomText/CustomText';
import CustomModal from '@components/CustomModal/CustomModal';
import AnimatedSprite from '@components/AnimatedSprite/AnimatedSprite';
import MainEggModal from './MainEggModal';

// 상수 및 유틸리티 관련 임포트
import { colors } from '@constants/colors';
import { petImages, petSpriteImages } from '@constants/petImage';

// API 및 데이터 관련 임포트
import { useMainPetInfo, useUpdateNickname } from '@hooks/queries/usePet';
import { fetchHealthData } from '@api/healthData';
import { saveDailyDiet, getDailyDiet } from '@api/healthDataApi';

// Zustand 스토어 관련 임포트
import useHealthDataStore from '@src/stores/healthDataStore';
import useEggModalDataStore from '@src/stores/eggModalDataStore';

function MainScreen({ navigation }) {
  const [isModalVisible, setModalVisible] = useState(false);
  const [petNickname, setPetNickname] = useState('');
  const [petLevel, setPetLevel] = useState(0);
  const [petPercent, setPetPercent] = useState(0);
  const [petBookId, setPetBookId] = useState('');
  const [isDark, setIsDark] = useState(false);

  const { data: mainPetInfo, isLoading } = useMainPetInfo();

  const { mutate } = useUpdateNickname();

  const { updateHealthData, updateDietData, checkQuestCompletion } =
    useHealthDataStore();

  const { shouldShowModal, newPetType, newPetStatus, setEggModalData } =
    useEggModalDataStore();
  const [step, setStep] = useState(1);
  const newPetImage = petImages[newPetType]?.[newPetStatus] || null;
  // 메인펫 정보가 바뀌면 업데이트
  useEffect(() => {
    if (mainPetInfo) {
      setPetNickname(mainPetInfo.petNickname);
      setPetBookId(mainPetInfo.petBookId);
      setPetLevel(mainPetInfo.petLevel);
      setPetPercent(mainPetInfo.petPercent);
    }
  }, [mainPetInfo]);

  // 헬스 데이터 업데이트
  useFocusEffect(
    useCallback(() => {
      const initializeHealthData = async () => {
        try {
          // 헬스 데이터를 가져옴
          const { steps, sleepHours } = await fetchHealthData();
          // 헬스 데이터 업데이트
          updateHealthData(steps, sleepHours);

          const dailyDiet = await getDailyDiet();
          updateDietData(dailyDiet);
          await checkQuestCompletion();
        } catch (error) {}
      };

      // 데이터 초기화 호출
      initializeHealthData();

      return () => {};
    }, [])
  );

  // 닉네임 변경 함수
  const handleUpdateNickname = () => {
    if (petNickname.trim()) {
      mutate({ petBookId, newNickname: petNickname.trim() });
      setModalVisible(false);
    } else {
      Alert.alert('Error', '닉네임을 입력해주세요');
    }
  };

  // 영양정보 함수 (사진촬영 + 푸드렌즈)
  const handleFoodRecognition = () => {
    launchCamera(
      {
        mediaType: 'photo',
        quality: 1,
        includeBase64: true,
      },
      (response) => {
        if (response.didCancel) {
          console.log('User cancelled image picker');
        } else if (response.errorCode) {
          console.error('ImagePicker Error: ', response.errorMessage);
        } else if (response.assets && response.assets[0]) {
          const imageBase64 = response.assets[0].base64;
          try {
            FoodLensModule.recognizeFood(imageBase64)
              .then((result) => {
                const parsedResult = JSON.parse(result);
                const transformedResult = {
                  calorie: parsedResult.energy ?? 0,
                  carbo: parsedResult.carbohydrate ?? 0,
                  protein: parsedResult.protein ?? 0,
                  fat: parsedResult.fat ?? 0,
                  sugar: parsedResult.sugar ?? 0,
                  sodium: parsedResult.sodium ?? 0,
                  transFat: parsedResult.transFat ?? 0,
                  saturatedFat: parsedResult.saturatedFat ?? 0,
                  cholesterol: parsedResult.cholesterol ?? 0,
                };

                saveDailyDiet(transformedResult)
                  .then(() => {
                    console.log('Diet saved successfully:', parsedResult);
                    Alert.alert('Recognition Successful');
                  })
                  .catch((error) => {
                    console.error('Save Error:', error);
                    Alert.alert(
                      'Save Error',
                      'Food recognized but failed to save the data.'
                    );
                  });
              })
              .catch((error) => {
                Alert.alert('Recognition Error', 'Failed to recognize food.');
              });
          } catch (error) {
            Alert.alert('Error', 'Failed to process the image.');
          }
        }
      }
    );
  };

  if (isLoading) {
    return <ActivityIndicator size="large" color={colors.MAIN_GREEN} />;
  }

  const petSpriteImage =
    petSpriteImages[mainPetInfo?.petType]?.[mainPetInfo?.petStatus] ||
    require('@assets/pets/beluga_egg.png');

  const petSpriteData = require('@assets/pets/sprite/sprite.json');

  const isDayTime = 6 <= new Date().getHours() < 18;

  const handleIsDark = () => {
    setIsDark(!isDark);
  };

  const backgroundImageSource = isDark
    ? require('@assets/backgrounds/main/sky_night.png')
    : require('@assets/backgrounds/main/sky_day.png');

  const frames = Object.values(petSpriteData.frames).map((frame) => ({
    x: frame.frame.x,
    y: frame.frame.y,
    w: frame.frame.w,
    h: frame.frame.h,
    duration: frame.duration,
  }));
  const animations = {
    walk: [0, 1, 0],
  };

  const handleEggUpdateNickname = async () => {
    if (petNickname.trim()) {
      try {
        mutate({ petBookId, newNickname: petNickname.trim() });
        setStep(3);
      } catch (error) {
        console.error('닉네임 업데이트 실패:', error);
      }
    } else {
      Alert.alert('Error', '닉네임을 입력해주세요!');
    }
  };

  const handleEggModalClose = async () => {
    setEggModalData({ shouldShowModal: false });

    setTimeout(() => {
      setStep(1);
    }, 300);
  };

  return (
    <View style={styles.container}>
      <ImageBackground
        source={backgroundImageSource}
        style={styles.backgroundImage}
        resizeMode="cover"
      >
        <MainEggModal
          isVisible={shouldShowModal}
          step={step}
          petNickname={petNickname}
          setPetNickname={setPetNickname}
          onUpdateNickname={handleEggUpdateNickname}
          onClose={handleEggModalClose}
          setStep={setStep}
          newPetImage={newPetImage}
        />
        <Pressable onPress={handleIsDark} style={styles.backgroundChangeBtn} />
        <View style={styles.header}>
          <CustomText style={styles.petName}>
            {mainPetInfo?.petNickname}
          </CustomText>

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
            onClose={() => {
              setPetNickname(mainPetInfo?.petNickname);
              setModalVisible(false);
            }}
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
            <CustomText style={styles.levelText}>{petLevel}</CustomText>
            <View style={styles.progressBar}>
              <View
                style={[styles.progressFill, { width: `${petPercent}%` }]}
              />
            </View>
          </View>
        </View>

        {/* 우측 메뉴 */}
        <View style={styles.rightMenu}>
          {/* 푸드렌즈 카메라 */}
          <Pressable onPress={handleFoodRecognition}>
            <MenuButton
              title={'식단기록'}
              icon={FoodLensIcon}
              isBlack={!isDark}
            ></MenuButton>
          </Pressable>
          {/* 퀘스트 모아보기 페이지로 이동 */}

          <Pressable onPress={() => navigation.navigate('Quest')}>
            <MenuButton
              title={'퀘스트'}
              icon={QuestIcon}
              isBlack={!isDark}
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
  },
  backgroundImage: {
    flex: 1,
    padding: 20,
  },
  backgroundChangeBtn: {
    position: 'absolute',
    right: 10,
    top: 10,
    backgroundColor: 'rgba(255, 255, 255, 0.2)',
    width: 10,
    height: 10,
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
