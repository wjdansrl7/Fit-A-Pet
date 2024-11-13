import React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { Pressable, StyleSheet, View } from 'react-native';

import { authNavigations } from '@src/constants';
import AuthHomeScreen from '@screens/auth/AuthHomeScreen';
import KakaoLoginScreen from '@screens/auth/KakaoLoginScreen';
import CustomText from '@components/CustomText/CustomText';
import MainScreen from '@screens/main/MainScreen';

const AuthStackNavigator = () => {
  // const { isLogin, isLoginLoading } = useAuth();
  const Stack = createNativeStackNavigator();
  return (
    <Stack.Navigator
      initialRouteName="Home"
      // 헤더 부분 커스텀
      screenOptions={() => ({
        headerStyle: {},
        // 타이틀 텍스트 스타일
        headerTitleStyle: {
          fontFamily: 'DungGeunMo',
          fontSize: 20,
          color: 'black',
        },
        headerTitleAlign: 'center',
      })}
    >
      <Stack.Screen
        name={authNavigations.AUTH_HOME}
        component={AuthHomeScreen}
        options={{ headerShown: false }}
      />
      <Stack.Screen
        name="Main"
        component={MainScreen}
        options={{ headerShown: false }}
      />
    </Stack.Navigator>
  );
};

const styles = StyleSheet.create({});

export default AuthStackNavigator;
