import React from 'react';
import { Dimensions, Image, Pressable, StyleSheet, View } from 'react-native';

import useAuth from '@src/hooks/queries/useAuth';
import { authNavigations } from '@src/constants';
import Config from 'react-native-config';
import queryClient from '@api/queryClient';

function AuthHomeScreen({ navigation }) {
  const { kakaoLoginMutation } = useAuth();

  const onClickKakoLogin = () => {
    kakaoLoginMutation.mutate(
      {},
      {
        onSuccess: ({ data }) => {
          // onSuccess: ({ accessToken, refreshToken }) => {
          setEncryptStorage('refreshToken', data.refreshToken);
          setHeader('Authorization', `Bearer ${data.accessToken}`);
          navigation.navigate('Main');
        },
        onError: () => {
          console.log('kakaoLoginMutation 에러');
          navigation.navigate('Main');
        },
        onSettled: () => {
          queryClient.refetchQueries({ queryKey: ['auth', 'getAccessToken'] });
          // queryClient.invalidateQueries({ queryKey: ['auth', 'getProfile'] });
        },
      }
    );
  };

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
        <Pressable
          onPress={() => onClickKakoLogin()}
          // onPress={() => navigation.navigate(authNavigations.KAKAO_LOGIN)}
        >
          <Image
            resizeMode="contain"
            style={styles.image}
            source={require('@assets/login/kakaoLogin.png')}
          />
        </Pressable>
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

export default AuthHomeScreen;
