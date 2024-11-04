import React, { useState } from 'react';
import { SafeAreaView, StyleSheet, Text } from 'react-native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { NavigationContainer } from '@react-navigation/native';
import MainScreen from '@screens/main/MainScreen';

const Stack = createNativeStackNavigator();

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(true);
  return (
    <NavigationContainer>
      {isLoggedIn ? (
        <Stack.Navigator screenOptions={{ headerShown: false }}>
          <Stack.Screen
            name="Main"
            component={MainScreen}
            options={{ title: '메인' }}
          />
        </Stack.Navigator>
      ) : null}
      {/* 여기에 로그인페이지 넣으세여 */}
    </NavigationContainer>
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
