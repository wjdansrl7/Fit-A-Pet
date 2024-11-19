import React, { useEffect, useState } from 'react';
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
import useAuth from '@hooks/queries/useAuth';
import useHealthDataStore from '@src/stores/healthDataStore';
import { getDailyDiet } from '@api/healthDataApi';

const sampleData = {
  calorie: 186.2,
  carbo: 12.8,
  carboRatio: 3.9,
  cholesterol: 36.51,
  cholesterolRatio: 12.2,
  fat: 9,
  fatRatio: 18,
  isCalorieEnough: false,
  isCalorieEnoughRatio: null,
  isCarboEnough: false,
  isCarboEnoughRatio: null,
  isFatEnough: true,
  isFatEnoughRatio: null,
  isProteinEnough: false,
  isProteinEnoughRatio: null,
  protein: 13.8,
  proteinRatio: 23,
  saturatedFat: 2.37,
  saturatedFatRatio: 107.7,
  sodium: 466.86,
  sodiumRatio: 23.3,
  sugar: 6.38,
  sugarRatio: 6.4,
  transFat: 0.07,
  transFatRatio: 0.5,
};

function NutritionCheck({ isEnough }) {
  return (
    <View style={styles.checkImageContainer}>
      {isEnough === null ? (
        <CustomText>-</CustomText>
      ) : isEnough ? (
        <Image
          resizeMode="contain"
          style={styles.checkImage}
          source={require('@assets/myInfo/O.png')}
        />
      ) : (
        <Image
          resizeMode="contain"
          style={styles.checkImage}
          source={require('@assets/myInfo/X.png')}
        />
      )}
    </View>
  );
}

function MyInfoScreen({ navigation }) {
  const [isModalVisible, setModalVisible] = useState(false);
  // const [dailyDiet, setDailyDiet] = useState(null);

  const { kakaoLogoutMutation } = useAuth();
  // const { mutate: kakaoLoginMutate } = kakaoLoginMutation();

  const onClickKakaoLogout = () => {
    // console.log('logout click');
    kakaoLogoutMutation.mutate();
  };

  // useEffect(() => {
  //   const fetchDietData = async () => {
  //     try {
  //       const data = await getDailyDiet(); // API 호출
  //       setDailyDiet(data); // 데이터 저장
  //       console.log('저장된데이터', dailyDiet);
  //       console.log(data);
  //     } catch (err) {
  //       console.log(err);
  //     }
  //   };
  //   fetchDietData();
  // }, []); // 컴포넌트 마운트 시 한 번 실행

  const { dietData } = useHealthDataStore();
  console.log(dietData);

  const nutritionFacts = [
    {
      nameEn: 'sodium',
      nameKr: '나트륨',
      serving: `${dietData.sodium}mg`,
      ratio: dietData.sodiumRatio,
    }, // 나트륨
    {
      nameEn: 'carbo',
      nameKr: '탄수화물',
      serving: `${dietData.carbo}g`,
      ratio: dietData.carboRatio,
    }, // 탄수화물
    {
      nameEn: 'sugar',
      nameKr: '당류',
      serving: `${dietData.sugar}g`,
      ratio: dietData.sugarRatio,
    }, // 당류
    {
      nameEn: 'fat',
      nameKr: '지방',
      serving: `${dietData.fat}g`,
      ratio: dietData.fatRatio,
    }, // 지방
    {
      nameEn: 'transFat',
      nameKr: '트랜스지방',
      serving: `${dietData.transFat}g`,
      ratio: dietData.transFatRatio,
    }, //  트랜스지방
    {
      nameEn: 'saturatedFat',
      nameKr: '포화지방',
      serving: `${dietData.saturatedFat}g`,
      ratio: dietData.saturatedFatRatio,
    }, //  포화지방
    {
      nameEn: 'cholesterol',
      nameKr: '콜레스테롤',
      serving: `${dietData.cholesterol}mg`,
      ratio: dietData.cholesterolRatio,
    }, //  콜레스테롤
    {
      nameEn: 'protein',
      nameKr: '단백질',
      serving: `${dietData.protein}g`,
      ratio: dietData.proteinRatio,
    }, // 단백질
  ];

  const { steps, sleepHours } = useHealthDataStore();

  return (
    <View style={styles.container}>
      {/* 일일기록 */}
      <View style={styles.screenContainer}>
        {/* 영양 */}
        <TouchableOpacity
          onPress={() => dietData && setModalVisible(true)}
          activeOpacity={0.8}
        >
          <View style={styles.categoryContainer}>
            <View
              style={{
                flexDirection: 'row',
                justifyContent: 'center',
                gap: 20,
              }}
            >
              <CustomText style={styles.defaultInfoText}>
                일일 섭취량: {dietData ? dietData.calorie.toFixed(2) : ' - '}
                kcal
              </CustomText>
              <NutritionCheck
                isEnough={dietData ? dietData.isCalorieEnough : null}
              />
            </View>
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
                  <NutritionCheck
                    isEnough={dietData ? dietData.isCalorieEnough : null}
                  />
                  <NutritionCheck
                    isEnough={dietData ? dietData.isProteinEnough : null}
                  />
                  <NutritionCheck
                    isEnough={dietData ? dietData.isFatEnough : null}
                  />
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
                총 섭취량: {dietData.calorie}kcal
              </CustomText>
              {/* <CustomText style={styles.c3}>{diets.calorie}kcal</CustomText> */}
            </View>

            <View
            //  style={styles.tableOut}
            >
              {nutritionFacts.map((nutritionFact, index) => (
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
              수면: {sleepHours.toFixed(1)}시간
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
              걸음: {steps}보
            </CustomText>
          </View>
        </View>
      </View>
      <View style={styles.loginLogout}>
        {/* 로그인 */}
        {/* <View style={styles.logoutButtonContainer}>
          <TouchableOpacity
            onPress={() => navigation.navigate(authNavigations.AUTH_HOME)}
            activeOpacity={0.5}
          >
            <CustomText style={styles.logoutButton}>로그인</CustomText>
          </TouchableOpacity>
        </View> */}
        {/* 로그아웃 */}
        <View style={styles.logoutButtonContainer}>
          <TouchableOpacity onPress={onClickKakaoLogout} activeOpacity={0.5}>
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
    // flex: 1,
  },
  screenContainer: {
    marginTop: 30,
    gap: 30,
  },
  categoryContainer: {
    marginTop: 20,
    gap: 20,
  },
  categoryContainerBody: {
    gap: 30,
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
  },
  imageContainer: {
    width: Dimensions.get('screen').width / 3,
    height: Dimensions.get('screen').width / 3,
    alignItems: 'center',
    justifyContent: 'center',
  },
  image: {
    width: '100%',
    height: '100%',
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
    fontSize: 16,
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
