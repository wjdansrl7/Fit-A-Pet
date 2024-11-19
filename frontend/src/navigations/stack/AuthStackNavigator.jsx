import React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { authNavigations } from '@src/constants';
import AuthHomeScreen from '@screens/auth/AuthHomeScreen';
import MainScreen from '@screens/main/MainScreen';

const AuthStackNavigator = () => {
  const Stack = createNativeStackNavigator();
  return (
    <Stack.Navigator
      initialRouteName="Home"
      screenOptions={() => ({
        headerStyle: {},
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

export default AuthStackNavigator;
