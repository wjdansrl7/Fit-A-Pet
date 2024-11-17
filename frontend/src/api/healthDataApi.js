import axiosInstance from './axios';

export const saveDailyDiet = async (dietInfo) => {
  // POST request to save diet data
  const { data } = await axiosInstance.post('/diets', dietInfo);
  console.log('받을게', data);
  return data;
};
