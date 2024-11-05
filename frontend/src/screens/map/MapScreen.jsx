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

const MapScreen = () => {
  const [isModalVisible, setModalVisible] = useState(false);

  return (
    <View style={styles.container}>
      <ImageBackground source={mapImg} style={styles.backgroundImage}>
        <TouchableOpacity
          onPress={() => setModalVisible(true)}
          activeOpacity={1}
        >
          {/* Custom 모달 예시 */}
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

          <TouchableOpacity
            onPress={() => setModalVisible(false)}
            activeOpacity={1}
          >
            <CustomText>모달 닫기</CustomText>
          </TouchableOpacity>
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
