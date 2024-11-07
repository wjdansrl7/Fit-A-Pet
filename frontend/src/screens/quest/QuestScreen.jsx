import React from 'react';
import { StyleSheet, View } from 'react-native';
import { colors } from '@src/constants';
import CustomText from '@components/CustomText/CustomText';
import QuestFrame from './QuestFrame';

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
    <View style={styles.backgroundColorWhite}>
      <View style={styles.container}>
        {/* 개인 퀘스트 */}
        <View style={styles.quest}>
          {/* 라벨 */}
          <View>
            <CustomText style={styles.questLabel}>개인 퀘스트</CustomText>
          </View>
          {/* 퀘스트 카드 */}
          <QuestFrame quest={quests.personal} />
        </View>

        {/* 그룹 퀘스트 */}
        <View style={styles.quest}>
          <View>
            <CustomText style={styles.questLabel}>그룹 퀘스트</CustomText>
          </View>
          {quests.group.map((quest, index) => (
            <QuestFrame key={index} quest={quest} />
          ))}
        </View>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
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
});

export default QuestScreen;
