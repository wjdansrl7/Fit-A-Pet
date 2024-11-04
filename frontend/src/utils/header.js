import axiosInstance from '@src/api/axios';

function setHeader() {
  axiosInstance.defaults.headers.common[key] = value;
}

function removeHeader() {
  if (!axiosInstance.defaults.headers.common[key]) {
    return;
  }
  delete axiosInstance.defaults.headers.common[key];
}

export { setHeader, removeHeader };
