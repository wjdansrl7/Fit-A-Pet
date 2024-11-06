import React from 'react';
import { Pressable, StyleSheet, View, Image, Dimensions } from 'react-native';
import CustomText from '@components/CustomText/CustomText';
import { authNavigations } from '@src/constants';

function MyInfoScreen({ navigation }) {
  const userInfo = {
    userName: '장성일',
    userTier: 'EASY',
    role: 'USER',
    petId: 1,
    petNickname: '뽀삐',
    petExp: 1500,
  };
  const diets = {
    calorie: 1700,
    carbo: 30,
    protein: 20,
    fat: 40,
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

      {/* 로그아웃 */}
      <View style={styles.logoutButtonContainer}>
        <Pressable onPress={() => navigation.navigate(authNavigations.LOGOUT)}>
          <CustomText style={styles.logoutButton}>로그아웃</CustomText>
        </Pressable>
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
});

export default MyInfoScreen;
