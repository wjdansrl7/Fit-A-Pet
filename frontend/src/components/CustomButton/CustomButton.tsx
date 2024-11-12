import React from 'react';
import { TouchableOpacity, StyleSheet } from 'react-native';
import CustomText from '@components/CustomText/CustomText';
import { colors } from '@src/constants';
const CustomButton = ({ title, onPress, style, textStyle }) => {
  return (
    <TouchableOpacity
      activeOpacity={0.8}
      style={[styles.button, style]}
      onPress={onPress}
    >
      <CustomText style={[styles.customFontText, textStyle]}>
        {title}
      </CustomText>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  button: {
    justifyContent: 'center',
    alignItems: 'center',
    borderRadius: 20,
    backgroundColor: colors.MAIN_GREEN,
    paddingHorizontal: 15,
    paddingVertical: 8,
  },
  customFontText: {
    fontSize: 20,
    fontFamily: 'DungGeunMo',
    color: colors.WHITE,
  },
});

export default CustomButton;
