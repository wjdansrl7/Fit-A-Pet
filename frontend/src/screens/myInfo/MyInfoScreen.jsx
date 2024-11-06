import React from 'react';
import { Pressable, StyleSheet, View } from 'react-native';
import CustomText from '@components/CustomText/CustomText';
import { authNavigations } from '@src/constants';

function MyInfoScreen({ navigation }) {
  const userInfo = {
    userName: '장성일',
    userTier: 'EASY',
    role: 'USER',
    petId: 1,
    petNickname: '뽀삐',
    petExp: 1500,
  };
  return (
    <View>
      {/* screenTitle */}
      <CustomText style={styles.screenTitle}>
        {userInfo.userName}님의 오늘 기록
      </CustomText>

      {/* 일일기록 */}
      <View>
        {/* 영양 */}
        <View></View>
        {/* 수면 */}
        <View></View>
        {/* 걸음수 */}
        <View></View>
      </View>

      {/* 로그아웃 */}
      <View style={styles.logoutButtonContainer}>
        <Pressable onPress={() => navigation.navigate(authNavigations.LOGOUT)}>
          <CustomText style={styles.logoutButton}>로그아웃</CustomText>
        </Pressable>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  screenTitle: {
    textAlign: 'center',
    fontSize: 24,
    marginVertical: 20,
  },
  logoutButtonContainer: {
    flexDirection: 'row',
    alignContent: 'center',
    justifyContent: 'center',
  },
  logoutButton: {
    // textDecorationLine: 'underline',
    borderBottomWidth: 1,
    textAlign: 'center',
    fontSize: 16,
  },
});

export default MyInfoScreen;
