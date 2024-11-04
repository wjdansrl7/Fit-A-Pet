import React from 'react';
import { View, Text, Pressable, StyleSheet, Image } from 'react-native';

function MenuButton({ title }) {
  return (
    <Pressable style={styles.menuButton}>
      <Image
        source={require('../../assets/heart_icon.png')} // 핑크 하트 이미지 경로
        style={styles.heartIcon}
      />
      <Text style={styles.menuText}>{title}</Text>
    </Pressable>
  );
}

const styles = StyleSheet.create({
  menuButton: {
    alignItems: 'center',
    marginBottom: 20,
  },
  heartIcon: {
    width: 70,
    height: 70,
  },
  menuText: {
    fontSize: 20,
  },
});

export default MenuButton;
