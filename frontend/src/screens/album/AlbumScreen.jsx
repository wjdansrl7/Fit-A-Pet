import React, { useEffect } from 'react';
import { View, Text, StyleSheet, Pressable, Image } from 'react-native';
import CustomText from '@components/CustomText/CustomText';

function AlbumScreen({}) {
  return (
    <View style={styles.container}>
      <Text>도감페이지입니당</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: 'tomato',
  },
});

export default AlbumScreen;
