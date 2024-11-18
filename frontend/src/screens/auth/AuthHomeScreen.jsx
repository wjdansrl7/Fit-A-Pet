import React from 'react';
import { Dimensions, Image, Pressable, StyleSheet, View } from 'react-native';

import { useKakaoLogin, useGetRefreshToken } from '@hooks/queries/useAuth';
import { authNavigations } from '@src/constants';
import Config from 'react-native-config';
import queryClient from '@api/queryClient';
import { setHeader } from '@src/utils/header';
import {
  setEncryptStorage,
  getEncryptStorage,
} from '@src/utils/encryptStorage';

function AuthHomeScreen({ navigation }) {
  // const { kakaoLoginMutation } = useAuth();
  const { mutate: kakaoLoginMutate } = useKakaoLogin();

  const onClickKakoLogin = () => {
    kakaoLoginMutate(
      {},
      {
        onSuccess: async (data) => {
          // onSuccess: ({ accessToken, refreshToken }) => {
          // console.log('data: ', data);
          setEncryptStorage('refreshToken', data.refreshToken);
          setHeader('Authorization', `Bearer ${data.accessToken}`);
          // const refreshToken = await getEncryptStorage('refreshToken');
          // console.log('저장된 refreshToken: ', refreshToken);
          // console.log('저장된 accessToken: ', data.accessToken);
          // console.log('로그인 완료 후 refreshToken,accessToken 저장 완료');

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
