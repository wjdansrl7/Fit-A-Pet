import React, { useState } from 'react';
import { QueryClientProvider } from '@tanstack/react-query';
import { StyleSheet } from 'react-native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { NavigationContainer } from '@react-navigation/native';
import MainScreen from '@screens/main/MainScreen';
import QuestScreen from '@screens/quest/QuestScreen';
import LoginScreen from '@screens/login/LoginScreen';
import queryClient from '@src/api/queryClient';
import useAuth from '@src/hooks/queries/useAuth';

const Stack = createNativeStackNavigator();

function App() {
  // const isLoggedIn = useAuth();
  const isLoggedIn = true;

  return (
    <QueryClientProvider client={queryClient}>
      <NavigationContainer>
        {isLoggedIn ? (
          <Stack.Navigator screenOptions={{ headerShown: false }}>
            {/* <Stack.Screen
            name="Main"
            component={MainScreen}
            options={{ title: '메인' }}
          /> */}
            <Stack.Screen
              name="Login"
              component={LoginScreen}
              options={{ title: '로그인' }}
            />
            <Stack.Screen
              name="Quest"
              component={QuestScreen}
              options={{ title: '퀘스트 모아보기' }}
            />
          </Stack.Navigator>
        ) : (
          <LoginScreen />
        )}
      </NavigationContainer>
    </QueryClientProvider>
  );
}

const styles = StyleSheet.create({
  backgroundStyle: {
    backgroundColor: 'black', // 배경 색상을 검정색으로 설정
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  textStyle: {
    color: 'white', // 텍스트 색상을 흰색으로 설정
  },
});

export default App;
