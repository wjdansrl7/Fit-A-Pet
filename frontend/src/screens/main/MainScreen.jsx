import React from 'react';
import { View, Text, StyleSheet, Image, Pressable } from 'react-native';
import MenuButton from './MenuButton';

function MainScreen() {
  return (
    <View style={styles.container}>
      {/* <Text>메인페이지</Text> */}

      {/* 상단 - 레벨 및 진행 상태 */}
      <View style={styles.header}>
        <Text style={styles.petName}>동규니</Text>
        <View style={styles.levelContainer}>
          <Text style={styles.levelText}>1</Text>
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
        <MenuButton title={'식단기록'}></MenuButton>
        <MenuButton title={'퀘스트'}></MenuButton>
      </View>

      {/* 하단 메뉴 */}
      <View style={styles.bottomMenu}>
        <MenuButton title={'그룹'}></MenuButton>
        <MenuButton title={'도감'}></MenuButton>
        <MenuButton title={'나의기록'}></MenuButton>
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
    padding: 10,
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
    fontWeight: 'bold',
    marginBottom: 5,
  },
  levelContainer: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  levelText: {
    fontWeight: 'bold',
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
    marginTop: 230,
    marginBottom: 20,
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
    position: 'absolute',
    bottom: 10,
    left: 10,
    right: 10,
  },
});

export default MainScreen;
