import { useEffect } from 'react';
// import { getAccessToken, postKakaoLogin, getProfile } from '@src/api/auth';
import {
  postAccessToken,
  postKakaoLogin,
  postLogout,
  getProfile,
} from '@src/api/authApi';
import { removeEncryptStorage } from '@src/utils';
import { setHeader, removeHeader } from '@src/utils/header';
import { useQuery, useMutation } from '@tanstack/react-query';
import queryClient from '@api/queryClient';

function useKakaoLogin() {
  return useMutation({
    mutationFn: postKakaoLogin,
    onSuccess: (data) => {
      // onSuccess: ({ accessToken, refreshToken }) => {
      console.log('accessToken: ', data.accessToken);
      setHeader('Authorization', `Bearer ${data.accessToken}`);
      setEncryptStorage('refreshToken', data.refreshToken);
      // navigation.navigate('Main');
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
      // 성공적으로 토큰을 받아왔을 때 헤더와 스토리지 설정
      console.log('usePostRefreshToken_accessToken: ', data.accessToken);
      setHeader('Authorization', `Bearer ${data.accessToken}`);
      setEncryptStorage('refreshToken', data.refreshToken);
    },
    onError: (error) => {
      console.log('usePostRefreshToken에러: ', error); // 실패 시 헤더와 스토리지 제거
      removeHeader('Authorization');
      removeEncryptStorage('refreshToken');
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
  return useMutation({
    mutationFn: postLogout,
    onSuccess: () => {
      removeHeader('Authorization');
      removeEncryptStorage('refreshToken');
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
  // const { data } = useGetProfile();
  // console.log('test', data);
  // const getProfileQuery = useGetProfile({
  //   // enabled: refreshTokenMutation.isSuccess,
  // });
  // console.log('s', getProfileQuery);
  // const isLogin = refreshTokenMutation.isSuccess;
  const isLogin = refreshTokenMutation.isSuccess;
  // console.log('isLogin: ', isLogin);
  const kakaoLoginMutation = useKakaoLogin();
  // const isLoginLoading = refreshTokenQuery.isPending;
  const kakaoLogoutMutation = usePostLogout();

  // return { kakaoLoginMutation, isLogin, kakaoLogoutMutation };
  // return { kakaoLoginMutation, isLogin, getProfileQuery, kakaoLogoutMutation };
  // return { kakaoLoginMutation, isLogin, getProfileQuery };
  return { kakaoLoginMutation, isLogin };
  // return { kakaoLoginMutation };
}

export default useAuth;
