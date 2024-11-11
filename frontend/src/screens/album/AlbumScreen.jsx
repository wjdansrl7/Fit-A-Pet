import React, { useEffect, useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  Pressable,
  Image,
  ScrollView,
  Button,
  FlatList,
} from 'react-native';
import axios from 'axios';
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
    isMain: false,
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
    isMain: true,
  },
  null,
  null,
];

const realData = [
  {
    petId: 1,
    petNickname: '뭉기',
    petType: '벨루가',
    petStatus: '성체',
  },
  {
    petId: 4,
    petNickname: '동규니',
    petType: '사자',
    petStatus: '알',
  },
];

function AlbumScreen() {
  const [loading, setLoading] = useState(true);
  const [petAlbumList, setPetAlbumList] = useState([]);
  const [selectedPet, setSelectedPet] = useState(null);
  const [isModalVisible, setModalVisible] = useState(false);

  // const getPetAlbumList = async () => {
  //   try {
  //     const response = await axios.get(
  //       'https://jsonplaceholder.typicode.com/posts'
  //     );
  //     setPetAlbumList(response.data);
  //   } catch (error) {
  //     console.log(error);
  //   } finally {
  //   }
  // };

  // useEffect(() => {
  //   getPetAlbumList();
  // }, []);

  const openModal = (pet) => {
    setSelectedPet(pet);
    setModalVisible(true);
  };

  const closeModal = () => {
    // setSelectedPet(null);
    setModalVisible(false);
  };

  const petGrid = Array.from({ length: 6 }).map((_, index) => {
    const pet = realData.find((p) => p.petId === index + 1); // 해당 petId로 위치 확인
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

      {/* <FlatList
        data={petGrid}
        numColumns={2}
        keyExtractor={(item) => item.petId.toString()}
        renderItem={({ item }) => (
          <AlbumFrame pet={item} onPress={() => item && openModal(item)} />
        )}
      /> */}

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
