import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { ActivityIndicator, Dimensions, StyleSheet, View } from 'react-native';
import WebView from 'react-native-webview';
import Config from 'react-native-config';

import { colors } from '@src/constants';
import useAuth from '@src/hooks/queries/useAuth';

import { login } from '@react-native-seoul/kakao-login';

// const REDIRECT_URI = `http://localhost:3030/auth/login`;
// const REDIRECT_URI = `http://10.0.2.2:3030/auth/login`;
// const REDIRECT_URI = `https://k11a603.p.ssafy.io/login/oauth2`;
// const REDIRECT_URI = `https://k11a603.p.ssafy.io/login/oauth2/code/kakao`;

function KakaoLoginScreen({ navigation }) {
  const signInWithKakao = async () => {
    try {
      const token = await login();
      // console.log('login success ', token.accessToken);

      // 2. accessToken을 포함하여 백엔드로 POST 요청 보내기
      // const response = await axios.post('http://70.12.246.179:8080/auth/kakao', {
      const response = await axios.post(
        'https://k11a603.p.ssafy.io/auth/kakao',
        {
          accessToken: token.accessToken,
        }
      );

      // console.log('Response from backend: ', response.data);
      setResult(response.data);
      // setResult(JSON.stringify(token));
      navigation.navigate('Main');
    } catch (err) {
      console.error('login err', err);
    }
  };

  useEffect(() => {
    signInWithKakao();
  }, []);

  return (
    <View style={styles.container}>
      {/* <View style={styles.kakaoLoadingContainer}>
          <ActivityIndicator size={'small'} color={colors.BLACK} />
        </View> */}
      {/* <WebView
        source={{
          // 하기의 쓰다만 코드는 임시용
          // uri: `https://kauth.kakao.com/oauth/authorize`,
          uri: `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${Config.KAKAO_REST_API_KEY}&redirect_uri=${REDIRECT_URI}`,
        }}
        onMessage={handleOnMessage}
        injectedJavaScript={"window.ReactNativeWebView.postMessage('')"}
        onNavigationStateChange={handleNavigationChangeState}
      /> */}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  kakaoLoadingContainer: {
    backgroundColor: colors.WHITE,
    height: Dimensions.get('window').height,
    paddingBottom: 100,
    alignItems: 'center',
    justifyContent: 'center',
  },
});

export default KakaoLoginScreen;
