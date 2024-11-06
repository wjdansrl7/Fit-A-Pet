import React, { useState } from 'react';
import {
  StyleSheet,
  View,
  TouchableOpacity,
  ImageBackground,
  Image,
} from 'react-native';
// import { useNavigation } from '@react-navigation/native';  페이지 이동 생기면
import CustomText from '@components/CustomText/CustomText';

import map2x from '@assets/backgrounds/group/map2x.webp';
import ActiveHouse from '@assets/backgrounds/group/ActiveHouse.png';
import InActiveHouse from '@assets/backgrounds/group/InActiveHouse.png';
import MapModal from '@screens/map/MapModal';

function MapScreen({ navigation }) {
  const [isModalVisible, setModalVisible] = useState(false);
  const [modalViewState, setModalViewState] = useState('init'); // init, create, join 중 하나로 상태 관리
  const [selectedHouse, setSelectedHouse] = useState(null);

  const houses = [
    {
      id: 1,
      name: 'House A',
      isActive: true,
      position: { top: 120, right: 20 },
    },
    {
      id: 2,
      name: 'House B',
      isActive: false,
      position: { top: 380, left: 40 },
    },
    {
      id: 3,
      name: 'House C',
      isActive: true,
      position: { bottom: 150, right: 20 },
    },
  ];

  const openCreateGroupModal = () => {
    setModalViewState('create');
  };

  const openJoinGroupModal = () => {
    setModalViewState('join');
  };

  const handleHouseClick = (house) => {
    if (house.isActive) {
      // 적힌 코드롤 같이 전해줘야지 특정 Group으로 이동
      navigation.navigate('Group');
    } else {
      setSelectedHouse(house);
      setModalViewState('init');
      setModalVisible(true);
    }
  };

  return (
    <View style={styles.container}>
      <ImageBackground
        source={map2x}
        style={styles.backgroundImage}
        resizeMode="cover"
      >
        {houses.map((house) => (
          <TouchableOpacity
            key={house.id}
            activeOpacity={0.9}
            style={[styles.house, house.position]}
            onPress={() => handleHouseClick(house)}
          >
            <Image
              source={house.isActive ? ActiveHouse : InActiveHouse}
              style={styles.overlayImage}
              resizeMode="contain"
            />
            {house.isActive && (
              <CustomText style={styles.houseName}>{house.name}</CustomText>
            )}
          </TouchableOpacity>
        ))}

        {/* 단일 모달 컴포넌트 */}
        <MapModal
          isVisible={isModalVisible}
          viewState={modalViewState} // init, create, join 중 하나
          onClose={() => setModalVisible(false)}
          onCreateGroup={openCreateGroupModal}
          onJoinGroup={openJoinGroupModal}
        />
      </ImageBackground>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  backgroundImage: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  overlayImage: {
    width: 100,
    height: 100,
  },
  house: {
    position: 'absolute',
    alignItems: 'center',
  },
  houseName: {
    marginTop: 5,
    color: 'white',
    fontSize: 16,
  },
});

export default MapScreen;
