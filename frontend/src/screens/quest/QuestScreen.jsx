import React from 'react';
import { Text, StyleSheet, View, Image, Dimensions } from 'react-native';
import { colors } from '@src/constants';
import CustomText from '@components/CustomText/CustomText';
// import EASY from '@assets/quest/EASY.png';
// import NORMAL from '@assets/quest/NORMAL.png';
// import HARD from '@assets/quest/HARD.png';

function QuestScreen() {
  const quests = {
    personal: {
      questId: 1,
      questCategory: 'WALK',
      questName: '동네 산책',
      questContent: '8000보 걷기',
      questTier: 'EASY',
      questReward: '경험치 100, 공적치 100',
    },
    group: [
      {
        questId: 3,
        questCategory: 'DIET',
        questName: '삼시세끼 탄단지 섭취',
        questContent: '하루 세끼 모두 탄단지 섭취하기',
        questTier: 'HARD',
        questReward: '경험치 100, 공적치 100',
      },
      {
        questId: 4,
        questCategory: 'SLEEP',
        questName: '내일은 주말!',
        questContent: '수면시간 9시간 채우기',
        questTier: 'NORMAL',
        questReward: '경험치 200, 공적치 100',
      },
      {
        questId: 5,
        questCategory: 'WALK',
        questName: '지옥의 행군..?',
        questContent: '15,000보 걷기',
        questTier: 'EASY',
        questReward: '경험치 150, 공적치 100',
      },
    ],
  };
  return (
    <View style={styles.container}>
      {/* 개인 퀘스트 */}
      {/* 라벨 */}
      <View>
        <CustomText style={styles.questLabel}>개인 퀘스트</CustomText>
      </View>
      {/* 퀘스트 카드 */}
      <View style={styles.questContainer}>
        {/* 퀘스트 카드 Header */}
        <View style={styles.questHeader}>
          <View>
            {quests.personal.questCategory == 'WALK' ? (
              <CustomText style={styles.questCategory}>걸음</CustomText>
            ) : quests.personal.questCategory == 'SLEEP' ? (
              <CustomText style={styles.questCategory}>수면</CustomText>
            ) : (
              <CustomText style={styles.questCategory}>영양</CustomText>
            )}
          </View>
          <View>
            <View style={styles.imageContainer}>
              {quests.personal.questTier == 'EASY' ? (
                <Image
                  resizeMode="contain"
                  style={styles.image}
                  source={require('@assets/quest/EASY.png')}
                />
              ) : quests.personal.questTier == 'NORMAL' ? (
                <Image
                  resizeMode="contain"
                  style={styles.image}
                  source={require('@assets/quest/NORMAL.png')}
                />
              ) : (
                <Image
                  resizeMode="contain"
                  style={styles.image}
                  source={require('@assets/quest/HARD.png')}
                />
              )}
            </View>
          </View>
        </View>
        {/* 퀘스트 카드 Body */}
        <View style={styles.questBody}>
          <View>
            <CustomText>
              [{quests.personal.questName}] {quests.personal.questContent}
            </CustomText>
          </View>
          <View>
            <CustomText style={styles.questReward}>
              ※ * ° ⁙ ⁘ 보상: {quests.personal.questReward}
            </CustomText>
          </View>
        </View>
      </View>
      {/* 그룹 퀘스트 */}
      <Text>퀘스트 스크린</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    gap: 20,
    margin: 20,
  },
  questContainer: {
    backgroundColor: colors.TAG_BLUE,
    // backgroundColor: colors.BACKGROUND_COLOR,
    borderRadius: 5,
    paddingHorizontal: 20,
    gap: 14,
    padding: 10,
  },
  questHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    // margin: 10,
  },
  questBody: {
    gap: 10,
  },
  questLabel: {
    backgroundColor: colors.MAIN_GREEN,
    borderRadius: 5,
    color: colors.WHITE,
    // marginHorizontal: 20,
    paddingVertical: 10,
    // justifyContent: 'center' ,
    // alignItems: 'center',
    textAlign: 'center',
  },
  questCategory: {
    backgroundColor: colors.TAG_RED,
    paddingVertical: 2,
    paddingHorizontal: 4,
    borderRadius: 5,
    color: colors.BLACK,
    textAlign: 'center',
    fontSize: 14,
  },
  imageContainer: {
    width: Dimensions.get('screen').width / 8,
    // backgroundColor: colors.TAG_RED,
    alignItems: 'center',
    justifyContent: 'center',
  },
  image: {
    width: '100%',
    // height: '100%',
  },
  questReward: {
    fontSize: 12,
    color: colors.MAIN_ORANGE,
  },
});

export default QuestScreen;
