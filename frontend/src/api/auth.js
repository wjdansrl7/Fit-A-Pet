import axiosInstance from './axios';

const postKakaoLogin = async () => {
  const { data } = await axiosInstance.post('/auth/login');
  return data;
};

// 로그인 후, 유저 정보 가져오는 api
// const getProfile = async () => {
//   const { data } = await axiosInstance.get('/auth/me');
// };

const getAccessToken = async () => {
  const refreshToken = await getEncryptStorage('refreshToken');
  const { data } = await axiosInstance.get('/auth/refresh', {
    headers: {
      Authorization: `Bearer ${refreshToken}`,
    },
  });
  return data;
};

const logout = async () => {
  await axiosInstance.post('/auth/logout');
};
export { postKakaoLogin, logout };
