import React, { useState, useEffect } from 'react';
import { StyleSheet, View, ActivityIndicator } from 'react-native';
import MainStackNavigator from '../stack/MainStackNavigator';
import AuthStackNavigator from '../stack/AuthStackNavigator';
import { colors } from '@constants/colors';
import useAuth from '@hooks/queries/useAuth';
import { getEncryptStorage } from '@src/utils';
import useAuthDataStore from '@src/stores/authDataStore';

function RootNavigator() {
  const { loginStatus } = useAuthDataStore();

  const { refreshTokenMutation } = useAuth();

  const [isLogin, setIsLogin] = useState(loginStatus);
  // console.log('RootNavigator에서 isLogin: ', isLogin);
  const [loading, setLoading] = useState(true); // 로딩 상태 관리

  // isLogin 상태가 결정되었을 때 로딩 종료
  useEffect(() => {
    refreshTokenMutation.mutate();

    if (loginStatus != undefined) {
      setLoading(false);
      setIsLogin(loginStatus);
    }
    // setIsLogin(loginStatus);
  }, [loginStatus]);

  // 로딩 중일 때 보여줄 UI
  if (loading) {
    return (
      <View style={styles.loadingContainer}>
        <ActivityIndicator size="large" color={colors.MAIN_GREEN} />
      </View>
    );
  }

  // 로그인 상태에 따른 네비게이터 렌더링
  return <>{isLogin ? <MainStackNavigator /> : <AuthStackNavigator />}</>;
}

const styles = StyleSheet.create({
  loadingContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#fff', // 배경색을 지정하여 깔끔하게 보여줄 수 있음
  },
});

export default RootNavigator;
