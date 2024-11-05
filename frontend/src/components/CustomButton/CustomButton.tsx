import React from 'react';
import { TouchableOpacity, StyleSheet } from 'react-native';
import CustomText from '@components/CustomText/CustomText';

const CustomButton = ({ title, onPress, style }) => {
  return (
    <TouchableOpacity
      activeOpacity={0.8}
      style={[styles.button, style]}
      onPress={onPress}
    >
      <CustomText style={styles.customFontText}>{title}</CustomText>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  button: {
    justifyContent: 'center',
    alignItems: 'center',
    borderRadius: 20,
    borderWidth: 2,
    borderColor: 'transparent',
    backgroundColor: 'green',
    padding: 15,
  },
  customFontText: {
    fontSize: 20,
    fontFamily: 'DungGeunMo',
    color: 'white',
  },
});

export default CustomButton;
