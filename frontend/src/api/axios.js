import axios from 'axios';

const axiosInstance = axios.create({
  baseURL: 'https://k11a603.p.ssafy.io',
  // baseURL: 'http://70.12.246.179:8080',
  // baseURL: 'http://localhost:3030',
  withCredentials: true,
});

export default axiosInstance;
