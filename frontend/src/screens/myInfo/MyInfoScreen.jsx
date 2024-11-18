import React, { useState } from 'react';
import {
  Pressable,
  TouchableOpacity,
  StyleSheet,
  View,
  Image,
  Dimensions,
} from 'react-native';
import CustomText from '@components/CustomText/CustomText';
import CustomModal from '@components/CustomModal/CustomModal';
import CustomButton from '@components/CustomButton/CustomButton';
import { authNavigations } from '@src/constants';

function MyInfoScreen({ navigation }) {
  const [isModalVisible, setModalVisible] = useState(false);

  const userInfo = {
    userName: '장성일',
    userTier: 'EASY',
    role: 'USER',
    petId: 1,
    petNickname: '뽀삐',
    petExp: 1500,
  };
  const diets = {
    calorie: 1700, // 열량
    nutritionFacts: [
      { nameEn: 'sodium', nameKr: '나트륨', serving: '220mg', ratio: 11 }, // 나트륨
      { nameEn: 'carbo', nameKr: '탄수화물', serving: '30g', ratio: 9 }, // 탄수화물
      { nameEn: 'totalSugars', nameKr: '당류', serving: '5.8g', ratio: 6 }, // 당류
      { nameEn: 'fat', nameKr: '지방', serving: '15g', ratio: 28 }, // 지방
      {
        nameEn: 'saturatedFattyAcid',
        nameKr: '트랜스지방',
        serving: '0g',
        ratio: 0,
      }, //  트랜스지방
      {
        nameEn: 'transFattyAcid',
        nameKr: '포화지방',
        serving: '2g',
        ratio: 13,
      }, //  포화지방
      {
        nameEn: 'cholesterol',
        nameKr: '콜레스테롤',
        serving: '4.9mg',
        ratio: 2,
      }, //  콜레스테롤
      { nameEn: 'protein', nameKr: '단백질', serving: '5.1g', ratio: 9 }, // 단백질
    ],
  };

  const sleeps = {
    sleepTime: 8,
  };
  const walks = {
    stepCnt: 8000,
  };
  return (
    <View style={styles.container}>
      {/* 스크린 타이틀 */}
      <CustomText style={styles.screenTitle}>
        {userInfo.userName}님의 오늘 기록
      </CustomText>

      {/* 일일기록 */}
      <View style={styles.screenContainer}>
        {/* 영양 */}
        <TouchableOpacity
          onPress={() => setModalVisible(true)}
          activeOpacity={0.8}
        >
          <View style={styles.categoryContainer}>
            <CustomText style={styles.defaultInfoText}>
              일일 섭취량: {diets.calorie}kcal
            </CustomText>
            <View style={styles.categoryContainerBody}>
              <View style={styles.imageContainer}>
                <Image
                  resizeMode="contain"
                  style={styles.image}
                  source={require('@assets/myInfo/diet.png')}
                />
              </View>
              <View style={styles.dietsInfo}>
                <View style={styles.dietsTextContainer}>
                  <CustomText style={styles.dietInfoText}>탄수화물</CustomText>
                  <CustomText style={styles.dietInfoText}>단백질</CustomText>
                  <CustomText style={styles.dietInfoText}>지방</CustomText>
                </View>
                <View style={styles.dietsCheckImageContainer}>
                  <View style={styles.checkImageContainer}>
                    <Image
                      resizeMode="contain"
                      style={styles.checkImage}
                      source={require('@assets/myInfo/O.png')}
                    />
                  </View>
                  <View style={styles.checkImageContainer}>
                    <Image
                      resizeMode="contain"
                      style={styles.checkImage}
                      source={require('@assets/myInfo/X.png')}
                    />
                  </View>
                  <View style={styles.checkImageContainer}>
                    <Image
                      resizeMode="contain"
                      style={styles.checkImage}
                      source={require('@assets/myInfo/O.png')}
                    />
                  </View>
                </View>
              </View>
            </View>
          </View>
        </TouchableOpacity>
        <CustomModal
          isVisible={isModalVisible}
          wantClose={true} // 불리언 값
          title="섭취한 영양소 "
          onClose={() => setModalVisible(false)} // 모달을 닫는 함수
        >
          <View>
            <View style={styles.row1}>
              {/* <CustomText style={styles.c1}>영양정보</CustomText> */}
              <CustomText style={styles.c2}>
                총 섭취량: {diets.calorie}kcal
              </CustomText>
              {/* <CustomText style={styles.c3}>{diets.calorie}kcal</CustomText> */}
            </View>

            <View
            //  style={styles.tableOut}
            >
              {diets.nutritionFacts.map((nutritionFact, index) => (
                <View key={index} style={styles.tableIn}>
                  <CustomText style={styles.tableC1}>
                    {nutritionFact.nameKr}
                  </CustomText>
                  <CustomText style={styles.tableC2}>
                    {nutritionFact.serving}
                  </CustomText>
                  <CustomText style={styles.tableC3}>
                    {nutritionFact.ratio}%
                  </CustomText>
                </View>
              ))}
            </View>

            <View>
              <CustomText style={styles.noticeText}>
                1일 영양성분 기준치에 대한 비율(%)은 2,000kcal 기준이므로 개인의
                필요 열량에 따라 다를 수 있습니다.
              </CustomText>
            </View>
          </View>
          {/* 모달 하단 버튼 예시 */}
          <CustomButton
            title="닫기!"
            style={{ backgroundColor: 'red' }}
            onPress={() => setModalVisible(false)}
          />
        </CustomModal>
        {/* 수면 */}
        <View style={styles.categoryContainer}>
          <View style={styles.categoryContainerBody}>
            <View style={styles.imageContainer}>
              <Image
                resizeMode="contain"
                style={styles.image}
                source={require('@assets/myInfo/sleep.png')}
              />
            </View>
            <CustomText style={styles.defaultInfoText}>
              수면: {sleeps.sleepTime}시간
            </CustomText>
          </View>
        </View>
        {/* 걸음수 */}
        <View style={styles.categoryContainer}>
          <View style={styles.categoryContainerBody}>
            <View style={styles.imageContainer}>
              <Image
                resizeMode="contain"
                style={styles.image}
                source={require('@assets/myInfo/walk.png')}
              />
            </View>
            <CustomText style={styles.defaultInfoText}>
              걸음수: {walks.stepCnt}보
            </CustomText>
          </View>
        </View>
      </View>
      <View style={styles.loginLogout}>
        {/* 로그인 */}
        <View style={styles.logoutButtonContainer}>
          <TouchableOpacity
            onPress={() => navigation.navigate(authNavigations.AUTH_HOME)}
            activeOpacity={0.5}
          >
            <CustomText style={styles.logoutButton}>로그인</CustomText>
          </TouchableOpacity>
        </View>
        {/* 로그아웃 */}
        <View style={styles.logoutButtonContainer}>
          <TouchableOpacity
            onPress={() => navigation.navigate(authNavigations.LOGOUT)}
            activeOpacity={0.5}
          >
            <CustomText style={styles.logoutButton}>로그아웃</CustomText>
          </TouchableOpacity>
        </View>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    gap: 50,
  },
  screenTitle: {
    textAlign: 'center',
    fontSize: 24,
    marginTop: 40,
  },
  screenContainer: {
    gap: 60,
  },
  categoryContainer: {
    gap: 20,
  },
  categoryContainerBody: {
    gap: 40,
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
  },
  imageContainer: {
    width: Dimensions.get('screen').width / 3,
    // backgroundColor: colors.TAG_RED,
    alignItems: 'center',
    justifyContent: 'center',
  },
  image: {
    width: '100%',
    // height: '100%',
  },
  dietsCheckImageContainer: {
    gap: 17,
    alignItems: 'center',
    justifyContent: 'center',
  },
  checkImageContainer: {
    height: 18,
    // backgroundColor: colors.TAG_RED,
    alignItems: 'center',
    justifyContent: 'center',
  },
  checkImage: {
    // width: '100%',
    height: '100%',
  },
  dietInfoText: {
    textAlign: 'center',
    fontSize: 18,
  },
  defaultInfoText: {
    textAlign: 'center',
    // fontSize: 18,
  },
  dietsInfo: {
    gap: 20,
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
  },
  dietsTextContainer: {
    gap: 16,
    alignItems: 'center',
    justifyContent: 'center',
  },

  logoutButtonContainer: {
    flexDirection: 'row',
    alignContent: 'center',
    justifyContent: 'center',
  },

  logoutButton: {
    // textDecorationLine: 'underline',
    borderBottomWidth: 1,
    textAlign: 'center',
    fontSize: 16,
    paddingTop: 40,
  },
  loginLogout: {
    flexDirection: 'row',
    gap: 40,
    justifyContent: 'center',
  },
  textAlignRight: {
    width: Dimensions.get('screen').width / 4,
    textAlign: 'right',
  },
  textAlignLeft: {
    textAlign: 'left',
  },
  textAlignCenter: {
    textAlign: 'center',
  },
  borderRight: {
    borderRightWidth: 2,
    gap: 6,
  },
  row1: {
    flexDirection: 'row',
    borderBottomWidth: 2,
    textAlign: 'center',
    // justifyContent: 'center',
    alignItems: 'center',
  },
  tableOut: {
    // flexDirection: 'row',
    // borderBottomWidth: 2,
    textAlign: 'center',
    // justifyContent: 'center',
    alignItems: 'center',
    // paddingVertical: 4,
  },
  tableIn: {
    flexDirection: 'row',
    borderBottomWidth: 2,
    textAlign: 'center',
    // justifyContent: 'center',
    alignItems: 'center',
  },
  tableC1: {
    width: (Dimensions.get('screen').width * 2.5) / 9,
    textAlign: 'right',
    borderRightWidth: 2,
    paddingRight: 4,
    paddingVertical: 4,
  },
  tableC2: {
    width: Dimensions.get('screen').width / 4,
    textAlign: 'right',
    borderRightWidth: 2,
    paddingRight: 4,
    paddingVertical: 4,
  },
  tableC3: {
    width: Dimensions.get('screen').width / 4,
    textAlign: 'right',
    paddingRight: 4,
    paddingVertical: 4,
  },
  c1: {
    width: Dimensions.get('screen').width / 4,
    textAlign: 'right',
    paddingRight: 4,
    paddingVertical: 4,
  },
  c2: {
    textAlign: 'center',
    paddingVertical: 4,
    justifyContent: 'center',
    alignItems: 'center',
  },

  noticeText: {
    fontSize: 12,
    paddingVertical: 4,
    borderBottomWidth: 2,
  },
});

export default MyInfoScreen;
