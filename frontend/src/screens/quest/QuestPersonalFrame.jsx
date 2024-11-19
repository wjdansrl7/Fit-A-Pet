import React from 'react';
import { StyleSheet, View, Dimensions } from 'react-native';
import { colors } from '@src/constants';
import CustomText from '@components/CustomText/CustomText';

function QuestPersonalFrame({ quests, seg }) {
  return (
    <View style={styles.questContainer}>
      {/* 퀘스트 카드 Header */}
      <View style={styles.questHeader}>
        {seg == 'WALK' ? (
          <CustomText
            style={[styles.questCategory, { backgroundColor: colors.TAG_RED }]}
          >
            걸음
          </CustomText>
        ) : seg == 'SLEEP' ? (
          <CustomText
            style={[styles.questCategory, { backgroundColor: colors.TAG_BLUE }]}
          >
            수면
          </CustomText>
        ) : (
          <CustomText
            style={[
              styles.questCategory,
              { backgroundColor: colors.TAG_GREEN },
            ]}
          >
            영양
          </CustomText>
        )}
      </View>
      {/* 퀘스트 카드 Body */}
      <View style={styles.questBody}>
        <View style={styles.rewardContainer1}>
          {quests.map((quest, index) => (
            <View style={styles.rewardContainer2} key={index}>
              {quest.questStatus ? (
                <View style={styles.rewardCircleCompleted}>
                  <CustomText style={styles.questNameCompleted}>
                    COMPLETE
                  </CustomText>
                </View>
              ) : (
                <View style={[styles[`rewardCircle${seg}`]]}>
                  <CustomText style={styles.questName}>
                    +{quest.questReward}
                  </CustomText>
                </View>
              )}
              <CustomText
                numberOfLines={1}
                style={[
                  styles.questName,
                  {
                    fontSize: 15,
                    width: Dimensions.get('screen').width / 5,
                    textAlign: 'center',
                  },
                ]}
              >
                {quest.questContent}
              </CustomText>
            </View>
          ))}
        </View>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
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
  questCategory: {
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
  questNameCompleted: {
    fontSize: 12,
    color: colors.MAIN_ORANGE,
  },
  imageContainer: {
    width: Dimensions.get('screen').width / 8,
    alignItems: 'center',
    justifyContent: 'center',
  },
  image: {
    width: '100%',
  },
  rewardContainer1: {
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    gap: 30,
  },
  rewardContainer2: {
    justifyContent: 'center',
    alignItems: 'center',
    gap: 8,
  },
  rewardCircleCompleted: {
    fontSize: 12,
    width: Dimensions.get('screen').width / 5,
    height: Dimensions.get('screen').width / 5,
    borderRadius: 180,
    borderColor: '#EBEBEB',
    borderWidth: 12,
    justifyContent: 'center',
    alignItems: 'center',
  },
  rewardCircleWALK: {
    backgroundColor: colors.WHITE,
    fontSize: 12,
    width: Dimensions.get('screen').width / 5,
    height: Dimensions.get('screen').width / 5,
    borderRadius: 180,
    borderColor: colors.TAG_RED,
    borderWidth: 12,
    justifyContent: 'center',
    alignItems: 'center',
  },
  rewardCircleSLEEP: {
    backgroundColor: colors.WHITE,
    fontSize: 12,
    width: Dimensions.get('screen').width / 5,
    height: Dimensions.get('screen').width / 5,
    borderRadius: 180,
    borderColor: colors.TAG_BLUE,
    borderWidth: 12,
    justifyContent: 'center',
    alignItems: 'center',
  },
  rewardCircleDIET: {
    backgroundColor: colors.WHITE,
    fontSize: 12,
    width: Dimensions.get('screen').width / 5,
    height: Dimensions.get('screen').width / 5,
    borderRadius: 180,
    borderColor: colors.TAG_GREEN,
    borderWidth: 12,
    justifyContent: 'center',
    alignItems: 'center',
  },
});

export default QuestPersonalFrame;
