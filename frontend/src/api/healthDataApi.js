import axiosInstance from './axios';

export const saveDailyDiet = async (dietInfo) => {
  const { data } = await axiosInstance.post('/diets', dietInfo);
  return data;
};

export const getDailyDiet = async () => {
  const { data } = await axiosInstance.get('/diets');
  return data;
};
