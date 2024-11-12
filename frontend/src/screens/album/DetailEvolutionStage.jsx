import React from 'react';
import { View, Image, Text, StyleSheet } from 'react-native';
import CustomText from '@components/CustomText/CustomText';
import { petImages } from '@constants/petImage';

function DetailEvolutionStage({ species, status }) {
  const petEvolutionStages = petImages[species];
  const stages = ['알', '준성체', '성체'];

  const getEvolutionImage = (stage) => {
    if (stages.indexOf(stage) > stages.indexOf(status)) {
      return require('@assets/pets/unknown_level.png');
    }
    return petEvolutionStages[stage];
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
