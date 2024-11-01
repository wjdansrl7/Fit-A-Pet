import React from 'react';
import { SafeAreaView, StyleSheet, Text } from 'react-native';

function App() {
  return (
    <SafeAreaView style={styles.backgroundStyle}>
      <Text style={styles.textStyle}>e</Text>
    </SafeAreaView>
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
