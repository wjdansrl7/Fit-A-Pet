import { getAccessToken, getProfilepostKakaoLogin } from '@src/api/auth';
import { axiosInstance } from '@src/api/axios';
import { queryClient } from '@src/api/queryClient';
import { removeEncryptStorage } from '@src/utils';
import { setHeader, removeHeader } from '@src/utils/header';
import { useMutation } from '@tanstack/react-query';
import { useEffect } from 'react';

function useKakaoLogin() {
  return useMutation({
    mutationFn: postKakaoLogin,
    onSuccess: ({ accessToken, refreshToken }) => {
      setEncryptStorage('refreshToken', refreshToken);
      setHeader('Authorization', `Bearer${accessToken}`);
    },
    onSettled: () => {
      queryClient.refetchQueries({ queryKey: ['auth', 'getAccessToken'] });
      queryClient.invalidateQueries({ queryKey: ['auth', 'getProfile'] });
    },
    ...mutationOptions,
  });
}

function useGetRefreshToken() {
  const { isSuccess, data, isError } = useQuery({
    queryKey: ['auth', 'getAccessToken'],
    queryFn: getAccessToken,
    staleTime: 1000 * 60 * 30 - 1000 * 60 * 3,
    refetchInterval: 1000 * 60 * 30 - 1000 * 60 * 3,
    refetchOnReconnect: true,
    refetchIntervalInBackground: true,
  });

  useEffect(() => {
    if (isSuccess) {
      setHeader('Authorization', `Bearer${data.accessToken}`);
      setEncryptStorage('refreshToken', data.refreshToken);
    }
  }, [isSuccess]);

  useEffect(() => {
    if (isError) {
      removeHeader('Authorization');
      removeEncryptStorage('refreshToken');
    }
  }, [isError]);
  return { isSuccess, isError };
}
