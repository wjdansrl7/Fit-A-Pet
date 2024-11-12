import React, { useEffect, useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  ActivityIndicator,
} from 'react-native';
import AlbumFrame from './AlbumFrame';
import AlbumDetailModal from './AlbumDetailModal';
import { usePetAlbumList } from '@hooks/queries/usePet';
import { colors } from '@constants/colors';

// 데이터모양
const pets = [
  {
    createdAt: '2024-11-11',
    petBookId: 1,
    petLevel: 1,
    petNickname: '소라게',
    petPercent: 0,
    petStatus: '알',
    petType: '사자',
    // isMain 어디감????
  },
];

function AlbumScreen() {
  const [selectedPet, setSelectedPet] = useState(null);
  const [isModalVisible, setModalVisible] = useState(false);

  const { data: petAlbumList, isLoading, isError } = usePetAlbumList();

  console.log('Fetched data:', petAlbumList); // 데이터 출력

  const openModal = (pet) => {
    setSelectedPet(pet);
    setModalVisible(true);
  };

  const closeModal = () => {
    setModalVisible(false);
  };

  if (isLoading) {
    return <ActivityIndicator size="large" color={colors.MAIN_GREEN} />;
  }

  if (isError) {
    return <Text>Error occurred: {isError.message}</Text>;
  }

  const petGrid = Array.from({ length: 6 }).map((_, index) => {
    const pet = petAlbumList.find((p) => p.petBookId === index + 1); // 해당 petId로 위치 확인
    return pet ? pet : { petId: index + 1 }; // 데이터가 없으면 기본값
  });

  return (
    <ScrollView contentContainerStyle={styles.container}>
      <View style={styles.grid}>
        {petGrid.map((pet, index) => (
          <AlbumFrame
            key={index}
            pet={pet}
            onPress={() => pet && openModal(pet)}
            style={styles.frameBorder}
          />
        ))}
      </View>

      <AlbumDetailModal
        isVisible={isModalVisible}
        onClose={closeModal}
        pet={selectedPet}
      />
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    flexGrow: 1,
    padding: 40,
    backgroundColor: '#FFF8DC',
  },

  grid: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    gap: 30,
  },
});

export default AlbumScreen;
