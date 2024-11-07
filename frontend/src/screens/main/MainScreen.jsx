import React, { useCallback, useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  Image,
  Pressable,
  TouchableOpacity,
  TextInput,
} from 'react-native';
import MenuButton from './MenuButton';
import CustomText from '@components/CustomText/CustomText';
import AlbumIcon from '@assets/icons/도감_icon.png';
import CustomModal from '@components/CustomModal/CustomModal';

function MainScreen({ navigation }) {
  const [isModalVisible, setModalVisible] = useState(false);
  const [petNickname, SetPetNickname] = useState('설정된 닉네임');

  return (
    <View style={styles.container}>
      {/* 상단 - 레벨 및 진행 상태 */}
      <View style={styles.header}>
        <View></View>
        <CustomText style={styles.petName}>동규니</CustomText>

        {/* 펫 닉네임 수정 */}
        <Pressable
          style={styles.petNameUpdate}
          onPress={() => setModalVisible(true)}
        >
          <Text>수정</Text>
        </Pressable>

        <CustomModal
          isVisible={isModalVisible}
          wantClose={true}
          title="닉네임 수정"
          onClose={() => setModalVisible(false)} // 모달을 닫는 함수
        >
          <TextInput
            style={styles.input}
            value={petNickname}
            onChangeText={SetPetNickname}
          />
        </CustomModal>

        <View style={styles.levelContainer}>
          <CustomText style={styles.levelText}>1</CustomText>
          <View style={styles.progressBar}>
            <View style={styles.progressFill} />
          </View>
        </View>
      </View>

      {/* 중앙 - 펫(알 모양) */}
      <View style={styles.petContainer}>
        <Image
          source={require('../../assets/pets/egg_gray_1.png')} // 알 이미지 경로
          style={styles.petImage}
        />
      </View>

      {/* 우측 메뉴 */}
      <View style={styles.rightMenu}>
        {/* 푸드렌즈 카메라 */}
        <MenuButton title={'식단기록'} icon={null}></MenuButton>
        {/* 퀘스트 모아보기 페이지로 이동 */}
        <MenuButton title={'퀘스트'} icon={null}></MenuButton>
      </View>

      {/* 하단 메뉴 */}
      <View style={styles.bottomMenu}>
        {/* 나머지 페이지 만들어지면 연결 */}
        <MenuButton title={'그룹'} icon={null}></MenuButton>
        <Pressable onPress={() => navigation.navigate('Album')}>
          <MenuButton title={'도감'} icon={AlbumIcon}></MenuButton>
        </Pressable>
        <MenuButton title={'나의기록'} icon={null}></MenuButton>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#C1EFFF', // 배경 하늘색
    padding: 20,
  },
  header: {
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: 15,
    marginTop: 30,
    marginBottom: 20,
    marginHorizontal: 20,
    backgroundColor: 'rgba(255, 255, 255, 0.5)',
    opacity: 100,
    borderRadius: 10,
    borderWidth: 2,
    borderColor: 'black',
  },
  petName: {
    fontSize: 26,
    // fontWeight: 'bold',
    marginBottom: 5,
    width: '80%',
    textAlign: 'center',
  },
  petNameUpdate: {
    backgroundColor: 'yellowgreen',
    position: 'absolute',
    right: 20,
    top: 20,
  },
  levelContainer: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  levelText: {
    // fontWeight: 'bold',
    fontSize: 30,
    backgroundColor: '#00BFFF',
    marginRight: -20,
    width: 50,
    height: 50,
    zIndex: 10,
    borderRadius: 40,
    textAlign: 'center',
    textAlignVertical: 'center',
  },
  progressBar: {
    width: 250,
    height: 30,
    backgroundColor: '#eee',
    borderRadius: 20,
  },
  progressFill: {
    width: '30%', // 진행 상태 (예: 30%)
    height: '100%',
    backgroundColor: '#FFD700', // 노란색
    borderRadius: 20,
  },
  petContainer: {
    alignItems: 'center',
    padding: 10,
    marginTop: 220,
    marginBottom: 20,
    backgroundColor: 'rgba(0, 0, 0, 0.2)',
  },
  petImage: {
    width: 270,
    height: 270,
  },
  rightMenu: {
    position: 'absolute',
    right: 30,
    top: 200,
  },
  bottomMenu: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    paddingHorizontal: 40,
    // position: 'absolute',
    backgroundColor: 'red',
  },

  input: {
    fontFamily: 'DungGeunMo',
    fontSize: 20,
    // backgroundColor: colors.BACKGROUND_COLOR,
    paddingLeft: 15,
    borderRadius: 20,
  },
});

export default MainScreen;
