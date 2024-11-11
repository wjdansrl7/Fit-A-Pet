import axiosInstance from './axios';

// 1. 진행중인 퀘스트 목록 조회
export const getQuests = async () => {
  const { data } = await axiosInstance.get(
    '/quests'
    // {
    // headers: {
    //   Authorization: `Bearer ${refreshToken}`,
    // },
    // }
  );
  return data;
};
