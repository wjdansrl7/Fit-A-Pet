import React, { useState } from 'react';
import { SafeAreaView, StyleSheet, Text } from 'react-native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { NavigationContainer } from '@react-navigation/native';
import MainScreen from '@screens/main/MainScreen';
import MapScreen from '@screens/map/MapScreen';

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
          <Stack.Screen
            name="Map"
            component={MapScreen}
            options={{ title: '지도' }}
          />
        </Stack.Navigator>
      ) : null}
      {/* 여기에 로그인페이지 넣으세여 */}
    </NavigationContainer>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1, // SafeAreaView가 전체 화면을 차지하도록 설정
  },
});

export default App;
