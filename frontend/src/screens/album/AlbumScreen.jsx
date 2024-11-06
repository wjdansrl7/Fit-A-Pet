import React, { useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  Pressable,
  Image,
  ScrollView,
  Button,
} from 'react-native';
import AlbumFrame from './AlbumFrame';
import AlbumDetailModal from './AlbumDetailModal';

const pets = [
  {
    type: '벨루가',
    name: '뭉기',
    image: require('../../assets/pets/beluga_3.png'),
    status: '성체',
    dateMet: '24-10-30',
    level: 40,
  },
  null,
  null,
  {
    type: '사자',
    name: '동규니',
    image: require('../../assets/pets/egg_gray_1.png'),
    status: '알',
    dateMet: '24-10-30',
    level: 1,
  },
  null,
  null,
  null,
  null,
];

function AlbumScreen() {
  const [selectedPet, setSelectedPet] = useState(null);
  const [isModalVisible, setModalVisible] = useState(false);

  const openModal = (pet) => {
    setSelectedPet(pet);
    setModalVisible(true);
  };

  const closeModal = () => {
    // setSelectedPet(null);
    setModalVisible(false);
  };

  return (
    <ScrollView contentContainerStyle={styles.container}>
      <View style={styles.grid}>
        {pets.map((pet, index) => (
          <AlbumFrame
            key={index}
            pet={pet}
            onPress={() => pet && openModal(pet)}
            style={styles.frameBorder}
          />
        ))}
      </View>

      {/* 실제 모달: 컴포넌트로 따로 */}
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
