import React from 'react';
import { StyleSheet, View, ImageBackground, Image } from 'react-native';
import map2x from '@assets/backgrounds/group/map2x.webp';
import house1x from '@assets/backgrounds/group/house1x.webp';
import house2x from '@assets/backgrounds/group/house2x.webp';
import house3x from '@assets/backgrounds/group/house3x.webp';
import house4x from '@assets/backgrounds/group/house4x.webp';

const MapScreen = () => {
  return (
    <View style={styles.container}>
      <ImageBackground
        source={map2x}
        style={styles.backgroundImage}
        resizeMode="cover"
      >
        {/* 두 번째 webp 이미지 */}
        <Image
          source={house4x}
          style={styles.overlayImage}
          resizeMode="contain"
        />
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
    justifyContent: 'center', // 텍스트를 중앙에 배치
    alignItems: 'center',
  },
  overlayImage: {
    position: 'absolute', // 원하는 위치에 배치하기 위해 절대 위치 사용
    top: '45%', // 화면 상단에서부터 20% 아래
    left: '10%', // 화면 왼쪽에서부터 30% 오른쪽으로 이동
    width: 100, // 이미지 너비
    height: 100, // 이미지 높이
  },
});

export default MapScreen;
