import React from 'react';
import { View, StyleSheet, Text, Image, Pressable } from 'react-native';
import CustomText from '@components/CustomText/CustomText';

function AlbumFrame({ pet, onPress }) {
  return (
    <View style={styles.container}>
      <View style={styles.doubleContainer}>
        {/* <Pressable onPress={onPress}> */}
        {pet ? (
          <Pressable style={{ alignItems: 'center' }} onPress={onPress}>
            <View style={styles.imageContainer}>
              <Image source={pet.image} style={styles.image} />
            </View>
            <CustomText style={styles.name}>{pet.name}</CustomText>
          </Pressable>
        ) : (
          <CustomText style={styles.placeholder}>?</CustomText>
        )}
        {/* </Pressable> */}
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    width: 150, // 사각형의 너비
    height: 200, // 사각형의 높이
    borderWidth: 4,
    borderColor: '#713C10',
    backgroundColor: 'white', // 배경 색상
    borderRadius: 8,
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
    backgroundColor: 'tomato',
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
    color: '#444',
  },
});

export default AlbumFrame;
