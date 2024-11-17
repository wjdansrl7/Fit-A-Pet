import axiosInstance from './axios';

export const saveDailyDiet = async (dietInfo) => {
  // POST request to save diet data
  const { data } = await axiosInstance.post('/diets', dietInfo);
  return data;
};
