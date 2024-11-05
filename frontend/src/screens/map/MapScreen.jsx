import React, { useState } from 'react';
import {
  StyleSheet,
  View,
  TouchableOpacity,
  ImageBackground,
  Image,
} from 'react-native';
import map2x from '@assets/backgrounds/group/map2x.webp';
import house1x from '@assets/backgrounds/group/house1x.webp';
import house2x from '@assets/backgrounds/group/house2x.webp';
import house3x from '@assets/backgrounds/group/house3x.webp';
import house4x from '@assets/backgrounds/group/house4x.webp';

import CustomModal from '@components/CustomModal/CustomModal';
import CustomText from '@components/CustomText/CustomText';
import CustomButton from '@components/CustomButton/CustomButton';

const MapScreen = () => {
  const [isModalVisible, setModalVisible] = useState(false);

  return (
    <View style={styles.container}>
      <ImageBackground
        source={map2x}
        style={styles.backgroundImage}
        resizeMode="cover"
      >
        <Image
          source={house4x}
          style={styles.overlayImage}
          resizeMode="contain"
        />
        {/* Custom 모달 예시 */}
        <TouchableOpacity
          onPress={() => setModalVisible(true)}
          activeOpacity={1}
        >
          <CustomText>모달 열기</CustomText>
        </TouchableOpacity>
        <CustomModal
          isVisible={isModalVisible}
          wantClose={true} // 불리언 값
          title="알을 받을까나?"
          onClose={() => setModalVisible(false)} // 모달을 닫는 함수
        >
          <CustomText>
            Lorem, ipsum dolor sit amet consectetur adipisicing elit.
            Voluptatibus iusto laboriosam nostrum officia error provident esse
            obcaecati molestias odit amet.
          </CustomText>
          {/* Custom 버튼 예시 */}
          <CustomButton
            title="아,, 패스!"
            style={{ backgroundColor: 'red' }}
            onPress={() => setModalVisible(false)}
          />
          {/*  */}
        </CustomModal>
        {/*  */}
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
    justifyContent: 'center',
    alignItems: 'center',
  },
});

export default MapScreen;
