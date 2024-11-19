import React, { useState, useEffect } from 'react';
import {
  StyleSheet,
  View,
  TouchableOpacity,
  ImageBackground,
  Image,
} from 'react-native';

// 컴포넌트 관련 임포트
import CustomText from '@components/CustomText/CustomText';
import MapModal from '@screens/map/MapModal';

// 애셋 관련 임포트
import map from '@assets/backgrounds/map/map.png';
import ActiveHouse from '@assets/backgrounds/map/ActiveHouse.png';
import InActiveHouse from '@assets/backgrounds/map/InActiveHouse.png';

// 상수 관련 임포트
import { colors } from '@src/constants';

// API 및 데이터 관련 임포트
import {
  useMapInfo,
  useCreateGuild,
  useJoinGuild,
} from '@hooks/queries/useMap';

function MapScreen({ navigation }) {
  const [isModalVisible, setModalVisible] = useState(false);
  const [modalViewState, setModalViewState] = useState('init');
  const [modalErrorState, setModalErrorState] = useState(null);
  const [selectedHouse, setSelectedHouse] = useState(null);
  const { data, isSuccess, refetch } = useMapInfo();
  const { mutateAsync: createGuildAsync } = useCreateGuild();
  const { mutateAsync: joinGuildAsync } = useJoinGuild();

  const housePosition = {
    1: { top: 80, right: 20 },
    2: { bottom: 200, left: 40 },
    3: { bottom: 150, right: 20 },
  };

  const initialHouses = [
    { id: -1, name: null, isActive: false, position: 1 },
    { id: -2, name: null, isActive: false, position: 2 },
    { id: -3, name: null, isActive: false, position: 3 },
  ];
  const [houses, setHouses] = useState(initialHouses);

  useEffect(() => {
    setHouses(initialHouses);
    if (isSuccess && data.length > 0) {
      const updatedHouses = initialHouses.map((house) => {
        const matchingGuild = data.find(
          (guild) => guild.guildPosition === house.position
        );

        if (matchingGuild) {
          return {
            ...house,
            id: matchingGuild.guildId,
            name: matchingGuild.guildName,
            isActive: true,
          };
        }
        return house;
      });
      setHouses(updatedHouses);
    }
  }, [data, isSuccess]);
  const handleCreateGuild = async (guildCreateInfo) => {
    try {
      const result = await createGuildAsync(guildCreateInfo);
      setModalErrorState(result);
      if (result === 'success') {
        await refetch();
        setModalVisible(false);
        setModalViewState('init');
        setModalErrorState(null);
      }
    } catch (error) {
      console.error('Unexpected error:', error);
    }
  };

  const handleJoinGuild = async (guildJoinInfo) => {
    try {
      const result = await joinGuildAsync(guildJoinInfo);
      setModalErrorState(result);
      if (result === 'success') {
        await refetch();
        setModalVisible(false);
        setModalViewState('init');
        setModalErrorState(null);
      }
    } catch (error) {
      console.error('Unexpected error:', error);
    }
  };

  const handleHouseClick = (house) => {
    if (house.isActive) {
      navigation.navigate('Guild', { guildId: house.id });
    } else {
      setSelectedHouse(house);
      setModalViewState('init');
      setModalVisible(true);
    }
  };

  return (
    <View style={styles.container}>
      <ImageBackground
        source={map}
        style={styles.backgroundImage}
        resizeMode="cover"
      >
        {houses.map((house) => (
          <TouchableOpacity
            key={house.id}
            activeOpacity={0.9}
            style={[styles.house, housePosition[house.position]]}
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

        <MapModal
          isVisible={isModalVisible}
          viewState={modalViewState}
          errorState={modalErrorState}
          selectedHouse={selectedHouse}
          setViewState={setModalViewState}
          setErrorState={setModalErrorState}
          onClose={() => setModalVisible(false)}
          onCreateGuild={handleCreateGuild}
          onJoinGuild={handleJoinGuild}
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
    color: colors.WHITE,
    fontSize: 20,
  },
});

export default MapScreen;
