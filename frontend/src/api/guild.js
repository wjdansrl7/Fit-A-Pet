import axiosInstance from './axios';

const getGuildInfo = async (guildId) => {
  const { data } = await axiosInstance.get(`/guilds/${guildId}/guild-info`);
  return data;
};

const getMemberInfo = async (guildId) => {
  const { data } = await axiosInstance.get(`/guilds/${guildId}/member-info`);
  return data;
};

const getQuestInfo = async (guildId) => {
  const { data } = await axiosInstance.get(`/guilds/${guildId}/quest-info`);
  return data;
};

const getEnteringCode = async (guildId) => {
  const { data } = await axiosInstance.get(`/guilds/${guildId}/entering-code`);
  return data;
};

const byeGuild = async (guildId) => {
  const { data } = await axiosInstance.delete(`/maps/guilds/${guildId}`);
  return data;
};

const getQuests = async () => {
  const { data } = await axiosInstance.get(`/quests/guilds`);
  return data;
};

const chooseQuest = async (guildId, questId) => {
  const { data } = await axiosInstance.put(
    `guilds/${guildId}/quests/${questId}`
  );
  return data;
};

export {
  getGuildInfo,
  getMemberInfo,
  getQuestInfo,
  getEnteringCode,
  byeGuild,
  getQuests,
  chooseQuest,
};
