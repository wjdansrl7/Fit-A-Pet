import React from 'react';
import { StyleSheet, View } from 'react-native';
import { colors } from '@src/constants';

function QuestScreen() {
  return <View></View>;
}

const styles = StyleSheet.create({
  container: {
    flex,
    gap: 20,
  },
  questContainer: {
    flex,
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
