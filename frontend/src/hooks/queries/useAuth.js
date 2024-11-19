import {
  postAccessToken,
  postKakaoLogin,
  postLogout,
  getProfile,
} from '@src/api/authApi';
import { setEncryptStorage } from '@src/utils/encryptStorage';
import { setHeader, removeHeader } from '@src/utils/header';
import { useQuery, useMutation } from '@tanstack/react-query';
import queryClient from '@api/queryClient';
import { useNavigation } from '@react-navigation/native';
import useAuthDataStore from '@src/stores/authDataStore';
import useEggModalDataStore from '@src/stores/eggModalDataStore';
import useHealthDataStore from '@src/stores/healthDataStore';
function useKakaoLogin() {
  const { setAuthData } = useAuthDataStore();

  const navigation = useNavigation();

  return useMutation({
    mutationFn: postKakaoLogin,
    onSuccess: (data) => {
      setHeader('Authorization', `Bearer ${data.accessToken}`);
      setEncryptStorage('refreshToken', data.refreshToken);
      setAuthData(true);

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
      // 성공적으로 토큰을 받아왔을 때 헤더와 스토리지 설정
      setHeader('Authorization', `Bearer ${data.accessToken}`);
      setEncryptStorage('refreshToken', data.refreshToken);
      setAuthData(true);
    },
    onError: (error) => {
      setAuthData(false);
    },
  });
}

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
  const resetEggModalData = useEggModalDataStore((state) => state.resetStore);
  const resetHealthData = useHealthDataStore((state) => state.resetStore);

  return useMutation({
    mutationFn: postLogout,
    onSuccess: () => {
      removeHeader('Authorization');
      setAuthData(false);
      navigation.navigate('AuthHome');
      queryClient.clear();
      resetEggModalData();
      resetHealthData();
    },
    onError: (error) => {
      console.log('usePostLogout에러 : ', error);
    },
  });
}

function useAuth() {
  const refreshTokenMutation = usePostRefreshToken();
  const kakaoLoginMutation = useKakaoLogin();
  const isLogin = kakaoLoginMutation.isSuccess;
  const kakaoLogoutMutation = usePostLogout();
  return {
    refreshTokenMutation,
    kakaoLoginMutation,
    isLogin,
    kakaoLogoutMutation,
  };
}

export default useAuth;
