import React from 'react';
import { StyleSheet, View, Image, Dimensions } from 'react-native';
import { colors } from '@src/constants';
import CustomText from '@components/CustomText/CustomText';

function QuestFrame({ quest }) {
  return (
    <View style={styles.questContainer}>
      {/* 퀘스트 카드 Header */}
      <View style={styles.questHeader}>
        <View>
          {quest.questCategory == 'WALK' ? (
            <CustomText style={styles.questCategoryWalk}>걸음</CustomText>
          ) : quest.questCategory == 'SLEEP' ? (
            <CustomText style={styles.questCategorySleep}>수면</CustomText>
          ) : (
            <CustomText style={styles.questCategoryDiet}>영양</CustomText>
          )}
        </View>
        <View>
          <View style={styles.imageContainer}>
            {quest.questTier == 'EASY' ? (
              <Image
                resizeMode="contain"
                style={styles.image}
                source={require('@assets/quest/EASY.png')}
              />
            ) : quest.questTier == 'NORMAL' ? (
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
          <CustomText style={styles.questName}>[{quest.questName}]</CustomText>
          <CustomText>{quest.questContent}</CustomText>
        </View>
        <View>
          <CustomText style={styles.questReward}>
            보상: {quest.questReward}
          </CustomText>
        </View>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
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

export default QuestFrame;
