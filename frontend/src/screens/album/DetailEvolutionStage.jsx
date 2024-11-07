import React from 'react';
import { View, Image, Text, StyleSheet } from 'react-native';
import CustomText from '@components/CustomText/CustomText';

// 이미지 하나씩 하드코딩 하슈
const petImages = {
  벨루가: {
    stage1: require('@assets/pets/beluga_3.png'),
    stage2: require('@assets/pets/beluga_3.png'),
    stage3: require('@assets/pets/beluga_3.png'),
  },
  사자: {
    stage1: require('@assets/pets/egg_gray_1.png'),
    stage2: require('@assets/pets/egg_gray_1.png'),
    stage3: require('@assets/pets/egg_gray_1.png'),
  },
};

function DetailEvolutionStage({ species, status }) {
  const petEvolutionStages = petImages[species];
  const stages = ['알', '준성체', '성체'];

  const getEvolutionImage = (stageIndex) => {
    if (stages.indexOf(stageIndex) > stages.indexOf(status)) {
      return require('@assets/pets/unknown_level.png');
    }

    switch (stageIndex) {
      case '준성체':
        return petEvolutionStages.stage2;
      case '성체':
        return petEvolutionStages.stage3;
      default:
        return petEvolutionStages.stage1;
    }
  };

  return (
    <View style={styles.evolutionContainer}>
      <Image source={getEvolutionImage('알')} style={styles.evolutionImage} />
      <CustomText style={styles.arrow}>→</CustomText>
      <Image
        source={getEvolutionImage('준성체')}
        style={styles.evolutionImage}
      />
      <CustomText style={styles.arrow}>→</CustomText>
      <Image source={getEvolutionImage('성체')} style={styles.evolutionImage} />
    </View>
  );
}

const styles = StyleSheet.create({
  evolutionContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 20,
    gap: 10,
  },
  evolutionImage: {
    width: 60,
    height: 60,
  },
  arrow: {
    fontSize: 40,
    color: 'dimgray',
  },
});

export default DetailEvolutionStage;
