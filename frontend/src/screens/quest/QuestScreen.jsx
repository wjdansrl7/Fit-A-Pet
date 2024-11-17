import React from 'react';
import { StyleSheet, View, ScrollView } from 'react-native';

import { colors } from '@src/constants';
import CustomText from '@components/CustomText/CustomText';
import QuestGroupFrame from './QuestGroupFrame';
import QuestPersonalFrame from './QuestPersonalFrame';

import { useGetQuests } from '@hooks/queries/useQuest';

function QuestScreen() {
  // const quests = {
  //   personalWalk: [
  //     {
  //       id: 1,
  //       questName: '동네 산책',
  //       questCategory: 'WALK',
  //       questContent: '5,000 걸음',
  //       questTier: 'EASY',
  //       questReward: 'EXP +100',
  //       questStatus: true,
  //     },
  //     {
  //       id: 2,
  //       questName: '동네 산책',
  //       questCategory: 'WALK',
  //       questContent: '8,000 걸음',
  //       questTier: 'NORMAL',
  //       questReward: 'EXP +100',
  //       questStatus: true,
  //     },
  //     {
  //       id: 3,
  //       questName: '동네 산책',
  //       questCategory: 'WALK',
  //       questContent: '12,000 걸음',
  //       questTier: 'HARD',
  //       questReward: 'EXP +100',
  //       questStatus: false,
  //     },
  //   ],
  //   personalSleep: [
  //     {
  //       id: 4,
  //       questName: '수면시간 보충!!',
  //       questCategory: 'SLEEP',
  //       questContent: '6시간',
  //       questTier: 'EASY',
  //       questReward: 'EXP +100',
  //       questStatus: true,
  //     },
  //     {
  //       id: 5,
  //       questName: '수면시간 보충!!',
  //       questCategory: 'SLEEP',
  //       questContent: '8시간',
  //       questTier: 'NORMAL',
  //       questReward: 'EXP +100',
  //       questStatus: true,
  //     },
  //     {
  //       id: 6,
  //       questName: '수면시간 보충!!',
  //       questCategory: 'SLEEP',
  //       questContent: '10시간',
  //       questTier: 'HARD',
  //       questReward: 'EXP +100',
  //       questStatus: false,
  //     },
  //   ],
  //   personalDiet: [
  //     {
  //       id: 7,
  //       questName: '탄단지 끼니 챙기기 ',
  //       questCategory: 'DIET',
  //       questContent: '한 끼',
  //       questTier: 'EASY',
  //       questReward: 'EXP +100',
  //       questStatus: true,
  //     },
  //     {
  //       id: 8,
  //       questName: '탄단지 끼니 챙기기 ',
  //       questCategory: 'DIET',
  //       questContent: '두 끼',
  //       questTier: 'NORMAL',
  //       questReward: 'EXP +100',
  //       questStatus: false,
  //     },
  //     {
  //       id: 9,
  //       questName: '탄단지 끼니 챙기기 ',
  //       questCategory: 'DIET',
  //       questContent: '세 끼',
  //       questTier: 'NORMAL',
  //       questReward: 'EXP +100',
  //       questStatus: false,
  //     },
  //   ],

  //   guildQuest: [
  //     {
  //       id: 10,
  //       questName: '지옥의 행군..?',
  //       questCategory: 'WALK',
  //       questContent: '15,000보 걷기',
  //       questTier: 'EASY',
  //       questReward: '경험치 150, 공적치 100',
  //       questStatus: false,
  //     },
  //     {
  //       id: 11,
  //       questName: '내일은 주말!',
  //       questCategory: 'SLEEP',
  //       questContent: '수면시간 9시간 채우기',
  //       questTier: 'NORMAL',
  //       questReward: '경험치 200, 공적치 100',
  //       questStatus: false,
  //     },
  //     {
  //       id: 12,
  //       questName: '삼시세끼 탄단지 섭취',
  //       questCategory: 'DIET',
  //       questContent: '하루 세끼 모두 탄단지 섭취하기',
  //       questTier: 'HARD',
  //       questReward: '경험치 100, 공적치 100',
  //       questStatus: false,
  //     },
  //   ],
  // };
  const { isSuccess, data, isError } = useGetQuests();
  console.log(data);

  return (
    <ScrollView contentContainerStyle={styles.scrollContainer}>
      <View style={styles.backgroundColorWhite}>
        {/* 퀘스트 리스트 */}
        {!isSuccess ? (
          <View>
            <CustomText>로딩중!!!!</CustomText>
          </View>
        ) : (
          <View style={styles.container}>
            {/* 개인 퀘스트 */}
            <View style={styles.quest}>
              {/* 라벨 */}
              <View>
                <CustomText style={styles.questLabel}>개인 퀘스트</CustomText>
              </View>

              <QuestPersonalFrame quests={data.personalWalk} seg={'WALK'} />
              <QuestPersonalFrame quests={data.personalSleep} seg={'SLEEP'} />
              <QuestPersonalFrame quests={data.personalDiet} seg={'DIET'} />
            </View>

            {/* 그룹 퀘스트 */}
            <View style={styles.quest}>
              <View>
                <CustomText style={styles.questLabel}>그룹 퀘스트</CustomText>
              </View>
              {data.guildQuest.map((quest, index) => (
                <View style={{ position: 'relative' }}>
                  <QuestGroupFrame key={index} quest={quest} />
                  <View></View>
                </View>
              ))}
            </View>
          </View>
        )}
      </View>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  scrollContainer: {
    flexGrow: 1,
  },
  backgroundColorWhite: {
    flex: 1,
    backgroundColor: colors.WHITE,
  },
  container: {
    gap: 20,
    margin: 20,
  },

  quest: {
    gap: 12,
  },
  questLabel: {
    backgroundColor: colors.MAIN_GREEN,
    borderRadius: 5,
    color: colors.WHITE,
    paddingVertical: 10,
    textAlign: 'center',
  },
  questContainer: {
    backgroundColor: colors.BACKGROUND_COLOR,
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
  questCategoryWalk: {
    backgroundColor: colors.TAG_RED,
    paddingVertical: 2,
    paddingHorizontal: 4,
    borderRadius: 5,
    color: colors.BLACK,
    textAlign: 'center',
    fontSize: 14,
  },
  questCategorySleep: {
    backgroundColor: colors.TAG_BLUE,
    paddingVertical: 2,
    paddingHorizontal: 4,
    borderRadius: 5,
    color: colors.BLACK,
    textAlign: 'center',
    fontSize: 14,
  },
  questCategoryDiet: {
    backgroundColor: colors.TAG_GREEN,
    paddingVertical: 2,
    paddingHorizontal: 4,
    borderRadius: 5,
    color: colors.BLACK,
    textAlign: 'center',
    fontSize: 14,
  },
  questName: {
    fontSize: 16,
  },
});

export default QuestScreen;
