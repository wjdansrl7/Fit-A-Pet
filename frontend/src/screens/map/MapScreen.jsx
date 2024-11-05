import React, { useState } from 'react';
import {
  StyleSheet,
  View,
  TouchableOpacity,
  ImageBackground,
} from 'react-native';
import mapImg from '@assets/backgrounds/group/mapImg.webp';
import CustomModal from '@components/CustomModal/CustomModal';
import CustomText from '@components/CustomText/CustomText';
import CustomButton from '@components/CustomButton/CustomButton';

const MapScreen = () => {
  const [isModalVisible, setModalVisible] = useState(false);

  return (
    <View style={styles.container}>
      <ImageBackground source={mapImg} style={styles.backgroundImage}>
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
    justifyContent: 'center',
    alignItems: 'center',
  },
});

export default MapScreen;
