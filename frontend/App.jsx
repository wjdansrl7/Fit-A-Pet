import React, { useState } from 'react';
import { Pressable, SafeAreaView, StyleSheet, Text, View } from 'react-native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { NavigationContainer } from '@react-navigation/native';
import MainScreen from '@screens/main/MainScreen';
import AlbumScreen from '@screens/album/AlbumScreen';
import CustomText from '@components/CustomText/CustomText';
import MapScreen from '@screens/map/MapScreen';
import GroupScreen from '@screens/group/GroupScreen';
const Stack = createNativeStackNavigator();

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(true);
  return (
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
