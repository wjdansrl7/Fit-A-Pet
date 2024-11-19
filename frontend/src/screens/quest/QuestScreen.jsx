import React from 'react';
import { StyleSheet, View, ScrollView } from 'react-native';

// 상수 관련 임포트
import { colors } from '@src/constants';

// 컴포넌트 관련 임포트
import CustomText from '@components/CustomText/CustomText';
import QuestGroupFrame from './QuestGroupFrame';
import QuestPersonalFrame from './QuestPersonalFrame';

// API 및 데이터 관련 임포트
import { useGetQuests } from '@hooks/queries/useQuest';

function QuestScreen() {
  const { isSuccess, data } = useGetQuests();

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
            {data.guildQuest.length == 0 ? (
              <></>
            ) : (
              <View style={styles.quest}>
                <View>
                  <CustomText style={styles.questLabel}>그룹 퀘스트</CustomText>
                </View>
                {data.guildQuest.map((quest, index) => (
                  <View style={{ position: 'relative' }}>
                    <QuestGroupFrame key={index} quest={quest} />
                  </View>
                ))}
              </View>
            )}
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
  completeQuest: {
    backgroundColor: colors.QUEST_COMPLETE,
    borderRadius: 5,
    paddingHorizontal: 20,
    gap: 14,
    padding: 10,
  },
  completeQuestText: {
    color: colors.MAIN_ORANGE,
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
    borderRadius: 5,
    paddingHorizontal: 20,
    gap: 14,
    padding: 10,
  },
  questHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
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
