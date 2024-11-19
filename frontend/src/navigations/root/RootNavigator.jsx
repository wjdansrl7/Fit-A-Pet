import React, { useState, useEffect } from 'react';
import { StyleSheet, View, ActivityIndicator } from 'react-native';
import MainStackNavigator from '../stack/MainStackNavigator';
import AuthStackNavigator from '../stack/AuthStackNavigator';
import { colors } from '@constants/colors';
import useAuth from '@hooks/queries/useAuth';
import useAuthDataStore from '@src/stores/authDataStore';

function RootNavigator() {
  const { loginStatus } = useAuthDataStore();

  const { refreshTokenMutation } = useAuth();

  const [isLogin, setIsLogin] = useState(loginStatus);
  const [loading, setLoading] = useState(true); // 로딩 상태 관리

  // isLogin 상태가 결정되었을 때 로딩 종료
  useEffect(() => {
    refreshTokenMutation.mutate();

    if (loginStatus != undefined) {
      setLoading(false);
      setIsLogin(loginStatus);
    }
  }, [loginStatus]);

  if (loading) {
    return (
      <View style={styles.loadingContainer}>
        <ActivityIndicator size="large" color={colors.MAIN_GREEN} />
      </View>
    );
  }

  return <>{isLogin ? <MainStackNavigator /> : <AuthStackNavigator />}</>;
}

const styles = StyleSheet.create({
  loadingContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#fff',
  },
});

export default RootNavigator;
