import React from 'react';
import { View, StyleSheet, Image, Pressable, Dimensions } from 'react-native';
import CustomText from '@components/CustomText/CustomText';
import { petImages } from '@constants/petImage';

function AlbumFrame({ pet, onPress }) {
  const petImage = petImages[pet.petType]?.[pet.petStatus] || null;
  return (
    <View style={styles.container}>
      <View style={styles.doubleContainer}>
        {petImage ? (
          <Pressable style={{ alignItems: 'center' }} onPress={onPress}>
            <View style={styles.imageContainer}>
              <Image source={petImage} style={styles.image} />
            </View>
            <CustomText style={styles.name}>{pet.petNickname}</CustomText>
          </Pressable>
        ) : (
          <CustomText style={styles.placeholder}>?</CustomText>
        )}
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    width: Dimensions.get('screen').width / 2.5,
    height: 200,
    borderWidth: 4,
    borderColor: '#713C10',
    backgroundColor: 'white',
    borderRadius: 8,
    marginBottom: 20,
  },

  doubleContainer: {
    margin: 4,
    flex: 1,
    backgroundColor: '#EFE3AF',
    justifyContent: 'center',
    alignItems: 'center',
    borderWidth: 4,
    borderColor: '#401F03',
  },

  imageContainer: {
    width: 100,
    height: 100,
  },
  image: {
    width: '100%',
    height: '100%',
  },
  name: {
    marginTop: 15,
  },
  placeholder: {
    fontSize: 120,
    color: '#555',
  },
});

export default AlbumFrame;
