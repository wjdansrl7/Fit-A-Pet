import React from 'react';
import { StyleSheet } from 'react-native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { NavigationContainer } from '@react-navigation/native';

import { QueryClientProvider } from '@tanstack/react-query';
import queryClient from '@src/api/queryClient';
import RootNavigator from '@src/navigations/root/RootNavigator';

const Stack = createNativeStackNavigator();

function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <NavigationContainer>
        <RootNavigator />
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
