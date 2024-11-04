import React from 'react';
import { Text, StyleSheet, View } from 'react-native';
import { colors } from '@src/constants';

function QuestScreen() {
  return (
    <View>
      <Text>퀘스트 스크린</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    gap: 20,
  },
  questContainer: {
    paddingHorizontal: 20,
    gap: 14,
  },
  questLabel: {
    backgroundColor: colors.MAIN_GREEN,
    borderRadius: 5,
    color: colors.WHITE,
  },
});

export default QuestScreen;
