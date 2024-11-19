import React from 'react';
import { View, StyleSheet, Image } from 'react-native';
import CustomText from '@components/CustomText/CustomText';

function MenuButton({ title, icon, isBlack }) {
  return (
    <View style={styles.menuButton}>
      <Image source={icon} style={styles.iconImage} />
      <CustomText
        style={[styles.menuText, isBlack ? { color: 'black' } : null]}
      >
        {title}
      </CustomText>
    </View>
  );
}

const styles = StyleSheet.create({
  menuButton: {
    alignItems: 'center',
    marginBottom: 20,
  },
  iconImage: {
    width: 70,
    height: 70,
  },
  menuText: {
    fontSize: 20,
    color: 'white',
  },
});

export default MenuButton;
