// import { getAccessToken, postKakaoLogin, getProfile } from '@src/api/auth';
import { getAccessToken, postKakaoLogin, postLogout } from '@src/api/authApi';
import { removeEncryptStorage } from '@src/utils';
import { setHeader, removeHeader } from '@src/utils/header';
import { useQuery, useMutation } from '@tanstack/react-query';
import { useEffect } from 'react';

export const useKakaoLogin = () => {
  const mutation = useMutation({
    mutationFn: postKakaoLogin,
    // onSuccess: ({ data }) => {
    //   // onSuccess: ({ accessToken, refreshToken }) => {
    //   setEncryptStorage('refreshToken', data.refreshToken);
    //   setHeader('Authorization', `Bearer${data.accessToken}`);
    //   navigation.navigate('Main');
    // },
    // onSettled: () => {
    //   queryClient.refetchQueries({ queryKey: ['auth', 'getAccessToken'] });
    //   // queryClient.invalidateQueries({ queryKey: ['auth', 'getProfile'] });
    // },
    // ...mutationOptions,
  });
  return mutation;
};

export const useGetRefreshToken = () => {
  const { isSuccess, isLoading, data, isError } = useQuery({
    queryKey: ['auth', 'getAccessToken'],
    queryFn: getAccessToken,
    staleTime: 1000 * 60 * 30 - 1000 * 60 * 3,
    refetchInterval: 1000 * 60 * 30 - 1000 * 60 * 3,
    refetchOnReconnect: true,
    refetchIntervalInBackground: true,
  });

  useEffect(() => {
    if (isSuccess) {
      setHeader('Authorization', `Bearer ${data.accessToken}`);
      setEncryptStorage('refreshToken', data.refreshToken);
    }
  }, [isSuccess]);

  useEffect(() => {
    if (isError) {
      removeHeader('Authorization');
      removeEncryptStorage('refreshToken');
    }
  }, [isError]);

  return { isSuccess, isLoading, isError };
};

export const usePostLogout = ({ navigation }) => {
  return useMutation({
    mutationFn: postLogout,
    onSuccess: () => {
      removeHeader('Authorization');
      removeEncryptStorage('refreshToken');
      navigation.navigate('AuthHomeScreen');
    },
  });
};

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
