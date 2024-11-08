import axios from 'axios';

const axiosInstance = axios.create({
  baseURL: 'https://k11a603.p.ssafy.io',
  // baseURL: 'http://localhost:3030',
  withCredentials: true,
});

export default axiosInstance;
