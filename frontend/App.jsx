import React from 'react';
import { SafeAreaView, StyleSheet } from 'react-native';
import MapScreen from '@screens/map/MapScreen';

function App() {
  return (
    <SafeAreaView style={styles.container}>
      <MapScreen />
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1, // SafeAreaView가 전체 화면을 차지하도록 설정
  },
});

export default App;
