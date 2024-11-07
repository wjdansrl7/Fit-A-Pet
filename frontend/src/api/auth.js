import axiosInstance from './axios';

// 1. 카카오 로그인
const postKakaoLogin = async (token) => {
  const { data } = await axiosInstance.post('/auth/login/kakao', { token });
  return data;
};

// 2. 로그인 후, 유저 정보 가져오는 api
const getProfile = async () => {
  const { data } = await axiosInstance.get(`/auth/${userId}`);
};

// 3. refreshToken 가지고 access토큰 받아오는 api
const getAccessToken = async () => {
  const refreshToken = await getEncryptStorage('refreshToken');
  const { data } = await axiosInstance.get('/auth/refresh', {
    headers: {
      Authorization: `Bearer ${refreshToken}`,
    },
  });
  return data;
};

// 4. 로그아웃 api
const logout = async () => {
  await axiosInstance.post('/auth/logout');
};
export { postKakaoLogin, getProfile, getAccessToken, logout };
