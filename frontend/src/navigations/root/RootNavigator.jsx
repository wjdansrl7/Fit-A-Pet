import React, { useState, useEffect } from 'react';
import { StyleSheet, View, ActivityIndicator } from 'react-native';
import MainStackNavigator from '../stack/MainStackNavigator';
import AuthStackNavigator from '../stack/AuthStackNavigator';
import { colors } from '@constants/colors';
import useAuth from '@hooks/queries/useAuth';
import { getEncryptStorage } from '@src/utils';

function RootNavigator() {
  const { refreshTokenMutation } = useAuth();

  // const loginStatus = getEncryptStorage('loginStatus');
  const [isLogin, setIsLogin] = useState(getEncryptStorage('loginStatus'));
  // const isLogin = false;
  console.log('loginStatus: ', getEncryptStorage('loginStatus'));
  const [loading, setLoading] = useState(true); // 로딩 상태 관리

  // isLogin 상태가 결정되었을 때 로딩 종료
  useEffect(() => {
    refreshTokenMutation.mutate();
    if (isLogin) {
      setLoading(false);
    }
    // setIsLogin(loginStatus);
  }, [isLogin]);

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
