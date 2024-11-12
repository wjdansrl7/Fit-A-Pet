import React from 'react';
import {
  View,
  Text,
  Image,
  Button,
  StyleSheet,
  Pressable,
  TouchableOpacity,
} from 'react-native';
import Modal from 'react-native-modal';
import CustomText from '@components/CustomText/CustomText';
import DetailEvolutionStage from './DetailEvolutionStage';

import { petImages } from '@constants/petImage';

// 일단 도감 전체에서 받아온 데이터 사용
// 실제로는 도감 상세 API 호출
const AlbumDetailModal = ({ isVisible, onClose, pet }) => {
  if (!pet) {
    return null;
  }

  const petImage = petImages[pet.petType]?.[pet.petStatus] || null;

  return (
    <Modal
      isVisible={isVisible}
      onBackdropPress={onClose}
      animationInTiming={500}
      animationOutTiming={500}
      backdropOpacity={0.5}
    >
      <View style={styles.modalContainer}>
        <View style={styles.doubleContainer}>
          <CustomText style={styles.title}>{pet.petNickname}</CustomText>
          {/* 닫기 버튼 */}
          <TouchableOpacity
            style={{ position: 'absolute', top: 20, right: 20 }}
            onPress={onClose}
          >
            <CustomText style={{ fontSize: 30 }}>⨉</CustomText>
          </TouchableOpacity>

          <Image source={petImage} style={styles.image} />

          {/* 진화 과정 */}
          <DetailEvolutionStage species={pet.petType} status={pet.petStatus} />

          <View style={styles.infoContainer}>
            {/* 알(레벨1) 상태에서는 ?표로 보여줌 */}
            <CustomText>
              개체: {pet.petStatus == '알' ? '?' : pet.petType}
            </CustomText>
            <CustomText>레벨: {pet.petLevel}</CustomText>
            <CustomText>만난 날: {pet.createdAt}</CustomText>
          </View>

          {/* 메인 펫 설정 버튼(상황에 따라 비활성화) */}
          {/* 메인 펫으로 설정하는 api 달기 */}
          <TouchableOpacity
            activeOpacity={0.8}
            style={[
              styles.button,
              pet.main ? { backgroundColor: 'gray' } : null,
            ]}
            onPress={onClose}
            disabled={pet.main}
          >
            <CustomText style={styles.buttonText}>
              {pet.main ? '메인 펫으로 설정 됨' : '메인 펫으로 설정'}
            </CustomText>
          </TouchableOpacity>
        </View>
      </View>
    </Modal>
  );
};

const styles = StyleSheet.create({
  modalContainer: {
    padding: 4,
    backgroundColor: 'white',
    borderWidth: 4,
    borderColor: '#713C10',
    borderRadius: 8,
    alignItems: 'center',
  },

  doubleContainer: {
    width: '100%',
    padding: 40,
    backgroundColor: '#FFF8DC',
    justifyContent: 'center',
    alignItems: 'center',
    borderWidth: 4,
    borderColor: '#401F03',
  },

  title: {
    fontSize: 30,
    marginBottom: 10,
  },
  image: {
    width: 150,
    height: 150,
    marginVertical: 20,
  },
  infoContainer: {
    marginBottom: 10,
    gap: 5,
  },

  button: {
    justifyContent: 'center',
    alignItems: 'center',
    borderRadius: 10,
    backgroundColor: 'seagreen',
    padding: 15,
    marginTop: 20,
  },
  buttonText: {
    color: 'white',
  },
});

export default AlbumDetailModal;
