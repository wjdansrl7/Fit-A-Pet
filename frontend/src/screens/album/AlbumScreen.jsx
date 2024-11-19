import React, { useState } from 'react';
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

const albumIndex = ['벨루가', '친칠라', '사자', '족제비', '범고래'];

function AlbumScreen() {
  const [selectedPet, setSelectedPet] = useState(null);
  const [isModalVisible, setModalVisible] = useState(false);
  const { data: petAlbumList, isLoading, isError, error } = usePetAlbumList();

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
    return <Text>Error occurred: {error.message}</Text>;
  }

  const petGrid = Array.from({ length: 8 }).map((_, index) => {
    const pet = petAlbumList.find(
      (p) => albumIndex.indexOf(p.petType) === index
    );
    return pet ? pet : { petId: index };
  });

  return (
    <ScrollView contentContainerStyle={styles.container}>
      <View style={styles.grid}>
        {petGrid.map((pet, index) => (
          <AlbumFrame
            key={index}
            pet={pet}
            onPress={() => pet && openModal(pet)}
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
    padding: 25,
    backgroundColor: '#FFF8DC',
  },

  grid: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    justifyContent: 'space-between',
    gap: 20,
    marginVertical: 20,
  },
});
5;
export default AlbumScreen;
