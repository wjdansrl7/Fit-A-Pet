import React from 'react';
import { View, Text, Pressable, StyleSheet, Image } from 'react-native';
import CustomText from '@components/CustomText/CustomText';

function MenuButton({ title, icon }) {
  return (
    <View style={styles.menuButton}>
      {icon ? (
        <Image
          source={icon} // 지정된 이미지 경로
          style={styles.iconImage}
        ></Image>
      ) : (
        <Image
          source={require('@assets/heart_icon.png')} // 핑크 하트 이미지 경로
          style={styles.iconImage}
        />
      )}
      <CustomText style={styles.menuText}>{title}</CustomText>
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
  },
});

export default MenuButton;
