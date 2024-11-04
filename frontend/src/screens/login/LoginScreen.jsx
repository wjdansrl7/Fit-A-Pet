import React from 'react';
import { Dimensions, Image, StyleSheet, Text, View } from 'react-native';
function LoginScreen() {
  return (
    <View style={styles.container}>
      <View style={styles.imageContainer}>
        <Image
          resizeMode="contain"
          style={styles.image}
          // source={logo}
          source={require('@assets/logo/logo.png')}
        />
      </View>
      <View style={styles.kakaoLoginContainer}>
        <Image
          resizeMode="contain"
          style={styles.image}
          source={require('@assets/login/kakaoLogin.png')}
        />
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    marginVertical: 250,
    alignItems: 'center',
    gap: 20,
  },
  imageContainer: {
    flex: 1,
    width: Dimensions.get('screen').width / 2,
  },
  kakaoLoginContainer: {
    flex: 1,
    width: (Dimensions.get('screen').width * 8) / 10,
  },

  image: {
    width: '100%',
    height: '100%',
  },
});

export default LoginScreen;
