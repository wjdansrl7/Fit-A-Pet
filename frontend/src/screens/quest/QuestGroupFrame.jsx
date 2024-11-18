import React, { useState } from 'react';
import { StyleSheet, View, Image, Dimensions } from 'react-native';
import { colors } from '@src/constants';
import CustomText from '@components/CustomText/CustomText';

function QuestGroupFrame({ quest }) {
  const [layout, setLayout] = useState({ width: 0, height: 0 });
  const [completeTextWidth, setCompleteTextWidth] = useState(0);
  const [completeTextHeight, setCompleteTextHeight] = useState(0);

  const handleLayout = (event) => {
    const { width, height } = event.nativeEvent.layout;
    setLayout({ width, height });
  };

  const handleCompleteTextLayout = (event) => {
    const { width, height } = event.nativeEvent.layout;
    setCompleteTextWidth(width);
    setCompleteTextHeight(height);
  };
  return (
    <>
      <View
        style={[styles[`questContainer${quest.questStatus}`]]}
        onLayout={handleLayout}
      >
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
            <CustomText style={styles.questName}>
              [{quest.questName}]
            </CustomText>
            <CustomText>{quest.questContent}</CustomText>
          </View>
          <View>
            <CustomText style={styles.questReward}>
              보상: +{quest.questReward}
            </CustomText>
          </View>
        </View>
      </View>
      {/* <View style={{ position: 'absolute' }}> */}
      {quest.questStatus && layout.width > 0 && layout.height > 0 && (
        <View
          style={[
            styles.questCompleteTextContainer,
            {
              top: layout.height / 2 - completeTextHeight / 2, // 부모 컴포넌트 1/2 높이에서 글자 높이의 절반을 빼서 중앙 정렬
              left: layout.width / 2 - completeTextWidth / 2, // 부모 컴포넌트 1/2 너비에서 글자 너비의 절반을 빼서 중앙 정렬
            },
          ]}
        >
          <CustomText
            style={styles.questCompleteText}
            onLayout={handleCompleteTextLayout}
          >
            COMPLETE
          </CustomText>
        </View>
      )}
    </>
  );
}

const styles = StyleSheet.create({
  questContainerfalse: {
    backgroundColor: colors.BACKGROUND_COLOR,
    // backgroundColor: colors.BACKGROUND_COLOR,
    borderRadius: 5,
    paddingHorizontal: 20,
    gap: 14,
    padding: 10,
  },
  questContainertrue: {
    backgroundColor: colors.BACKGROUND_COLOR,
    opacity: 0.4,
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

  questCompleteTextContainer: {
    position: 'absolute',
    alignItems: 'center',
    justifyContent: 'center',
  },
  questCompleteText: {
    fontSize: 50,
    color: colors.MAIN_ORANGE,
  },
});

export default QuestGroupFrame;
