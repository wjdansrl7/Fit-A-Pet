import axios from 'axios';

const axiosInstance = axios.create({
  baseURL: 'https://k11a603.p.ssafy.io',
  withCredentials: true,
});

export default axiosInstance;
