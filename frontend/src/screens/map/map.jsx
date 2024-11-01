import React from 'react';
import { StyleSheet, Text, View, ImageBackground } from 'react-native';
import mapImg from '@assets/backgrounds/group/mapImg.webp'; // 이미지 절대경로 import

const Map = () => {
  return (
    <View style={styles.container}>
      <ImageBackground source={mapImg} style={styles.backgroundImage}>
        <Text style={styles.text}>map</Text>
      </ImageBackground>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  backgroundImage: {
    flex: 1,
    justifyContent: 'center', // 텍스트를 가운데 정렬
    alignItems: 'center', // 텍스트를 가운데 정렬
  },
  text: {
    color: 'white', // 배경과 대비되는 색상 설정
    fontSize: 24,
    fontWeight: 'bold',
  },
});

export default Map;
