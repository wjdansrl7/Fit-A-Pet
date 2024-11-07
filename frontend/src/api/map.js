import axiosInstance from './axios';

const getMapInfo = async () => {
  const { data } = await axiosInstance.get(`/maps`);
  return data;
};

const guildCreateInfo = {
  guildPostion: 1,
  guildName: 'ssafy',
};

const createGuild = async (guildCreateInfo) => {
  const { data } = await axiosInstance.post(
    `/maps/create-guild`,
    guildCreateInfo
  );
  return data;
};

const guildJoinInfo = {
  guildPostion: 1,
  enteringCode: 'ssafy',
};

const joinGuild = async (guildJoinInfo) => {
  const { data } = await axiosInstance.post(`/maps/join-guild`, guildJoinInfo);
  return data;
};

export { getMapInfo, createGuild, joinGuild };
