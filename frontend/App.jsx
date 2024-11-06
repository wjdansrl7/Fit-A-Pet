import React, { useState } from 'react';
import { Pressable, SafeAreaView, StyleSheet, Text, View } from 'react-native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { NavigationContainer } from '@react-navigation/native';

import { QueryClientProvider } from '@tanstack/react-query';
import queryClient from '@src/api/queryClient';
import { authNavigations } from '@src/constants';
import useAuth from '@src/hooks/queries/useAuth';

import MainScreen from '@screens/main/MainScreen';
import AuthHomeScreen from '@screens/auth/AuthHomeScreen';
import KakaoLoginScreen from '@screens/auth/KakaoLoginScreen';
import QuestScreen from '@screens/quest/QuestScreen';
import AlbumScreen from '@screens/album/AlbumScreen';
import CustomText from '@components/CustomText/CustomText';
import MapScreen from '@screens/map/MapScreen';
import GroupScreen from '@screens/group/GroupScreen';
const Stack = createNativeStackNavigator();

function App() {
  // const isLoggedIn = useAuth();
  const isLoggedIn = true;

  return (
    <QueryClientProvider client={queryClient}>
      <NavigationContainer>
        {isLoggedIn ? (
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
              name="Group"
              component={GroupScreen}
              options={{ title: '그룹' }}
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
          </Stack.Navigator>
        ) : null}
      </NavigationContainer>
    </QueryClientProvider>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1, // SafeAreaView가 전체 화면을 차지하도록 설정
  },
});

export default App;
