import React, { useState } from 'react';

import { ActivityIndicator, Dimensions, StyleSheet, View } from 'react-native';
import WebView from 'react-native-webview';
import Config from 'react-native-config';

import { colors } from '@src/constants';
import useAuth from '@src/hooks/queries/useAuth';

// const REDIRECT_URI = `http://localhost:3030/auth/login`;
// const REDIRECT_URI = `http://10.0.2.2:3030/auth/login`;
const REDIRECT_URI = `https://k11a603.p.ssafy.io/login/oauth2`;
// const REDIRECT_URI = `https://k11a603.p.ssafy.io/login/oauth2/code/kakao`;

function KakaoLoginScreen() {
  const { kakaoLoginMutation } = useAuth();
  const [isLoading, setIsLoading] = useState(false);
  const [isChangeNavigate, setIsChangeNavigate] = useState(true);

  const handleOnMessage = (event) => {
    if (event.nativeEvent.url.includes(`${REDIRECT_URI}?code=`)) {
      console.log(event);
      const code = event.nativeEvent.url.replace(`${REDIRECT_URI}?code=`, '');
      console.log(code);
      // requestToken(code);
    }
  };

  const requestToken = async (code) => {
    const response = await axios({
      method: 'post',
      url: 'https://kauth.kakao.com/oauth/token',
      params: {
        grant_type: 'authorization_code',
        client_id: Config.KAKAO_REST_API_KEY,
        redirect_uri: REDIRECT_URI,
        code,
      },
    });
    console.log(response.data);
    // kakaoLoginMutation.mutate(response.data.access_token);
  };

  const handleNavigationChangeState = (event) => {
    const isMatched = event.url.includes(`${REDIRECT_URI}?code=`);
    setIsLoading(isMatched);
    setIsChangeNavigate(event.loading);
  };

  return (
    <View style={styles.container}>
      {(isLoading || isChangeNavigate) && (
        <View style={styles.kakaoLoadingContainer}>
          <ActivityIndicator size={'small'} color={colors.BLACK} />
        </View>
      )}
      <WebView
        source={{
          // 하기의 쓰다만 코드는 임시용
          // uri: `https://kauth.kakao.com/oauth/authorize`,
          uri: `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${Config.KAKAO_REST_API_KEY}&redirect_uri=${REDIRECT_URI}`,
        }}
        onMessage={handleOnMessage}
        injectedJavaScript={"window.ReactNativeWebView.postMessage('')"}
        onNavigationStateChange={handleNavigationChangeState}
      />
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
