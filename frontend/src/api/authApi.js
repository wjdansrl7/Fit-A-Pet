import axiosInstance from './axios';
import { login } from '@react-native-seoul/kakao-login';

// 1. 카카오 로그인
const postKakaoLogin = async () => {
  try {
    const token = await login();
    // console.log('login success ', token.accessToken);

    // 2. accessToken을 포함하여 백엔드로 POST 요청 보내기
    // const response = await axios.post('http://70.12.246.179:8080/auth/kakao', {
    const response = await axiosInstance.post(
      '/auth/kakao',
      // const response = await axios.post(
      //   'https://k11a603.p.ssafy.io/auth/kakao',
      {
        accessToken: token.accessToken,
      }
    );

    // console.log('Response from backend: ', response.data.body);
    // setResult(response.data);
    // setResult(JSON.stringify(token));
    // navigation.navigate('Main');
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
  await axiosInstance.post('/auth/logout');
};
export { postKakaoLogin, getAccessToken, postLogout };
// export { postKakaoLogin, getProfile, getAccessToken, logout };
