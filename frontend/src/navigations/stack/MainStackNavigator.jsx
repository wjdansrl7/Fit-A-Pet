import React from 'react';
import { Pressable, StyleSheet, View } from 'react-native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';

import CustomText from '@components/CustomText/CustomText';
import { authNavigations } from '@src/constants';
import AlbumScreen from '@screens/album/AlbumScreen';
import GuildScreen from '@screens/guild/GuildScreen';
import MainScreen from '@screens/main/MainScreen';
import MapScreen from '@screens/map/MapScreen';
import QuestScreen from '@screens/quest/QuestScreen';
import KakaoLoginScreen from '@screens/auth/KakaoLoginScreen';
import AuthHomeScreen from '@screens/auth/AuthHomeScreen';
import MyInfoScreen from '@screens/myInfo/MyInfoScreen';
import Logout from '@screens/auth/Logout';

const MainStackNavigator = () => {
  const Stack = createNativeStackNavigator();

  return (
    <Stack.Navigator
      initialRouteName="Home"
      // 헤더 부분 커스텀
      screenOptions={({ navigation }) => ({
        headerStyle: {},
        // 타이틀 텍스트 스타일
        headerTitleStyle: {
          fontFamily: 'DungGeunMo',
          fontSize: 20,
          color: 'black',
        },
        headerTitleAlign: 'center',
        headerLeft: () => (
          <Pressable onPress={navigation.goBack}>
            <CustomText
              style={{
                fontSize: 30,
              }}
            >
              {'<'}
            </CustomText>
          </Pressable>
        ),
      })}
    >
      {/* {
        isLoggedIn ? ( */}
      <Stack.Screen
        name="Main"
        component={MainScreen}
        options={{ headerShown: false }}
      />
      <Stack.Screen
        name="Album"
        component={AlbumScreen}
        options={{ title: '도감' }}
      />
      <Stack.Screen
        name="Map"
        component={MapScreen}
        options={{ title: '지도' }}
      />
      <Stack.Screen
        name="Guild"
        component={GuildScreen}
        options={{ title: '길드' }}
      />
      <Stack.Screen
        name={authNavigations.AUTH_HOME}
        component={AuthHomeScreen}
        options={{ title: '로고와 로그인' }}
      />
      <Stack.Screen
        name={authNavigations.KAKAO_LOGIN}
        component={KakaoLoginScreen}
        options={{ title: '카카오 로그인' }}
      />
      <Stack.Screen
        name="Quest"
        component={QuestScreen}
        options={{ title: '퀘스트 모아보기' }}
      />
      <Stack.Screen
        name="MyInfo"
        component={MyInfoScreen}
        options={{ title: '나의 기록' }}
      />
      <Stack.Screen
        name={authNavigations.LOGOUT}
        component={Logout}
        options={{ title: '로그아웃' }}
      />

      {/* ) : null

      } */}
    </Stack.Navigator>
  );
};

const styles = StyleSheet.create({});

export default MainStackNavigator;
