import React from 'react';
import {
  View,
  Text,
  StyleSheet,
  Pressable,
  Image,
  ScrollView,
} from 'react-native';
import CustomText from '@components/CustomText/CustomText';
import AlbumFrame from './AlbumFrame';

const pets = [
  { name: '뭉기', image: require('../../assets/pets/beluga_3.png') },
  null,
  null,
  { name: '동규니', image: require('../../assets/pets/egg_gray_1.png') },
  null,
  null,
  null,
  null,
];

function AlbumScreen() {
  return (
    <ScrollView contentContainerStyle={styles.container}>
      <View style={styles.grid}>
        {pets.map((pet, index) => (
          <AlbumFrame key={index} pet={pet} style={styles.frameBorder} />
        ))}
      </View>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    flexGrow: 1,
    padding: 40,
    backgroundColor: '#FFF7D2',
  },

  grid: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    gap: 30,
  },
});

export default AlbumScreen;
