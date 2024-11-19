import axiosInstance from './axios';
import { login } from '@react-native-seoul/kakao-login';
import { getEncryptStorage } from '@src/utils';

// 1. 카카오 로그인
const postKakaoLogin = async () => {
  try {
    const token = await login();
    const response = await axiosInstance.post('/auth/kakao', {
      accessToken: token.accessToken,
    });
    return response.data.body;
  } catch (err) {
    console.error('login err', err);
  }
};

// 2. refreshToken 가지고 access토큰 받아오는 api
const getAccessToken = async () => {
  const refreshToken = await getEncryptStorage('refreshToken');
  const { data } = await axiosInstance.get('/auth/reissue', {
    headers: {
      Authorization: `Bearer ${refreshToken}`,
    },
  });
  return data;
};

// 3. 로그아웃 api
const postLogout = async () => {
  const refreshToken = await getEncryptStorage('refreshToken');

  await axiosInstance.post(
    '/auth/logout',
    {},
    {
      headers: {
        Authorization: `Bearer ${refreshToken}`,
      },
    }
  );
};
export { postKakaoLogin, getAccessToken, postLogout };
