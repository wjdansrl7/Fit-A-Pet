import { useEffect } from 'react';
// import { getAccessToken, postKakaoLogin, getProfile } from '@src/api/auth';
import {
  postAccessToken,
  postKakaoLogin,
  postLogout,
  getProfile,
} from '@src/api/authApi';
import {
  setEncryptStorage,
  removeEncryptStorage,
} from '@src/utils/encryptStorage';
import { setHeader, removeHeader } from '@src/utils/header';
import { useQuery, useMutation } from '@tanstack/react-query';
import queryClient from '@api/queryClient';
import { useNavigation } from '@react-navigation/native';
import useAuthDataStore from '@src/stores/authDataStore';
import useEggModalDataStore from '@src/stores/eggModalDataStore';

function useKakaoLogin() {
  const { setAuthData } = useAuthDataStore();

  const navigation = useNavigation();

  return useMutation({
    mutationFn: postKakaoLogin,
    onSuccess: (data) => {
      // onSuccess: ({ accessToken, refreshToken }) => {
      // console.log('accessToken: ', data.accessToken);
      setHeader('Authorization', `Bearer ${data.accessToken}`);
      setEncryptStorage('refreshToken', data.refreshToken);
      setAuthData(true);
      // setEncryptStorage('loginStatus', true);
      // navigation.navigate('Main');
      // 알얻는 로직 돌리기
      const navigateParams = {
        shouldShowModal: data.shouldShowModal,
      };
      // shouldShowModal이 true일 경우에만 newPetType과 newPetStatus 추가
      if (data.shouldShowModal) {
        navigateParams.newPetType = data.petType;
        navigateParams.newPetStatus = data.petStatus;
        const { setEggModalData } = useEggModalDataStore.getState();
        setEggModalData(navigateParams);
      }

      navigation.navigate('Main');
    },
    onSettled: () => {
      // queryClient.refetchQueries({ queryKey: ['auth', 'getAccessToken'] });
      // queryClient.invalidateQueries({ queryKey: ['auth', 'getProfile'] });
    },
  });
}

function usePostRefreshToken() {
  return useMutation({
    mutationFn: postAccessToken,
    onSuccess: (data) => {
      console.log('usePostRefreshToken: ', data);
      // 성공적으로 토큰을 받아왔을 때 헤더와 스토리지 설정
      // console.log('usePostRefreshToken_accessToken: ', data.accessToken);
      setHeader('Authorization', `Bearer ${data.accessToken}`);
      setEncryptStorage('refreshToken', data.refreshToken);
      setAuthData(true);
    },
    onError: (error) => {
      // console.log('usePostRefreshToken에러: ', error); // 실패 시 헤더와 스토리지 제거
      // removeHeader('Authorization');
      // removeEncryptStorage('refreshToken');
      setAuthData(false);
    },
  });
}

// function useGetProfile() {
//   return useQuery({
//     queryFn: getProfile,
//     queryKey: ['auth', 'getProfile'],
//   });
// }
function useGetProfile() {
  const { isSuccess, data, isLoading, refetch } = useQuery({
    queryKey: ['auth', 'getProfile'],
    queryFn: getProfile,
    staleTime: 0,
    refetchOnWindowFocus: true,
    refetchOnReconnect: true,
  });

  return { isSuccess, data, isLoading, refetch };
}

function usePostLogout() {
  const { setAuthData } = useAuthDataStore();
  const navigation = useNavigation();

  return useMutation({
    mutationFn: postLogout,
    onSuccess: () => {
      // console.log('로그아웃전_refreshToken', getEncryptStorage('refreshToken'));
      // console.log('로그아웃전_loginStatus', getEncryptStorage('loginStatus'));
      removeHeader('Authorization');
      // removeEncryptStorage('refreshToken');
      setAuthData(false);
      // removeEncryptStorage('loginStatus');
      // console.log('로그아웃_refreshToken', getEncryptStorage('refreshToken'));
      // console.log('로그아웃_loginStatus', getEncryptStorage('loginStatus'));
      navigation.navigate('AuthHome');
    },
    onError: (error) => {
      console.log('usePostLogout에러 : ', error); // 실패 시 헤더와 스토리지 제거
    },
  });
}

// function useAuth() {
//   // const kakaoLoginMutation = useKakaoLogin();
//   const refreshTokenQuery = useGetRefreshToken();
//   // const getProfileQuery = useGetProfile({
//   //   enabled: refreshTokenQuery.isSuccess,
//   // });88
//   const isLogin = refreshTokenQuery.isSuccess;
//   const kakaoLoginMutation = useKakaoLogin();

//   return { kakaoLoginMutation, isLogin };
//   // return { kakaoLoginMutation, isLogin, getProfileQuery };
// }

// export default useAuth;
function useAuth() {
  const refreshTokenMutation = usePostRefreshToken();
  // console.log('useAuth_refreshTokenMutation', refreshTokenMutation.isSuccess);
  // const getProfileQuery = useGetProfile({
  //   // enabled: refreshTokenMutation.isSuccess,
  // });
  // console.log('s', getProfileQuery);
  // const isLogin = refreshTokenMutation.isSuccess;
  // console.log('isLogin: ', isLogin);
  const kakaoLoginMutation = useKakaoLogin();
  const isLogin = kakaoLoginMutation.isSuccess;
  // console.log('useAuth_kakaoLoginMutation_isLogin', isLogin);

  // const isLoginLoading = refreshTokenQuery.isPending;
  const kakaoLogoutMutation = usePostLogout();

  // return { kakaoLoginMutation, isLogin, kakaoLogoutMutation };
  // return { kakaoLoginMutation, isLogin, getProfileQuery, kakaoLogoutMutation };
  // return { kakaoLoginMutation, isLogin, getProfileQuery };
  return {
    refreshTokenMutation,
    kakaoLoginMutation,
    isLogin,
    kakaoLogoutMutation,
  };
  // return { kakaoLoginMutation };
}

export default useAuth;
