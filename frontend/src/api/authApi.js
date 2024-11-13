import axiosInstance from './axios';
import { login } from '@react-native-seoul/kakao-login';
import { getEncryptStorage } from '@src/utils/encryptStorage';

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

// 2. 로그인 후, 유저 정보 받아오는 api
const getProfile = async () => {
  const { data } = await axiosInstance.get('/auth/info');

  return data;
};

// 3. refreshToken 가지고 access토큰 받아오는 api
const postAccessToken = async () => {
  console.log('getAccessToken api 시작');
  const refreshToken = await getEncryptStorage('refreshToken');
  console.log('refreshToken :', refreshToken);

  const { data } = await axiosInstance.post(
    '/auth/reissue',
    {},
    {
      headers: {
        Authorization: `Bearer ${refreshToken}`,
        // Authorization: `Bearer <${refreshToken}>`,
      },
    }
  );
  console.log('postAccessToken: ', data);
  return data;
};

// 3. 로그아웃 api
const postLogout = async () => {
  const refreshToken = await getEncryptStorage('refreshToken');
  console.log('postLogout_refreshToken :', refreshToken);
  const { data } = await axiosInstance.post(
    '/auth/logout',
    {},
    {
      headers: {
        Authorization: `Bearer ${refreshToken}`,
      },
    }
  );
  console.log('postLogout: ', data);
  return data;
};
export { postKakaoLogin, postAccessToken, postLogout, getProfile };
// export { postKakaoLogin, getProfile, getAccessToken, logout };
