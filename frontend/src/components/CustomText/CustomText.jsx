import React from 'react';
import { Text, StyleSheet } from 'react-native';
import { colors } from '@src/constants';
const CustomText = ({ children, style, ...props }) => {
  return (
    <Text style={[styles.customFontText, style]} {...props}>
      {children}
    </Text>
  );
};

const styles = StyleSheet.create({
  customFontText: {
    fontSize: 20,
    fontFamily: 'DungGeunMo', // 추가한 폰트 이름
    color: colors.BLACK,
  },
});

export default CustomText;
