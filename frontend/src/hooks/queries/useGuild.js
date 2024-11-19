import queryClient from '@src/api/queryClient';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import {
  getGuildInfo,
  getMemberInfo,
  getQuestInfo,
  byeGuild,
  getQuests,
  chooseQuest,
  getMyInfo,
} from '@api/guild';

// clear
function useGuildInfo(guildId) {
  const { isSuccess, data, isLoading } = useQuery({
    queryKey: ['guild', 'guildInfo', guildId],
    queryFn: () => getGuildInfo(guildId),
    staleTime: 0,
    // staleTime: 1000 * 60 * 30,
    // cacheTime: 1000 * 60 * 60,
    refetchOnWindowFocus: true,
    refetchOnReconnect: true,
  });
  return { isSuccess, data, isLoading };
}

function useMemberInfo(guildId) {
  const { isSuccess, data, isLoading } = useQuery({
    queryKey: ['guild', 'memberInfo', guildId],
    queryFn: () => getMemberInfo(guildId),
    staleTime: 0,
    cacheTime: 0,
    refetchOnWindowFocus: true,
    refetchOnReconnect: true,
  });
  return { isSuccess, data, isLoading };
}

function useQuestInfo(guildId) {
  const { isSuccess, data, isLoading } = useQuery({
    queryKey: ['guild', 'questInfo', guildId],
    queryFn: () => getQuestInfo(guildId),
    staleTime: 0,
    // staleTime: 1000 * 60 * 5,
    // cacheTime: 1000 * 60 * 10,
    refetchOnWindowFocus: true,
    refetchOnReconnect: true,
    refetchInterval: 1000 * 60 * 5,
  });
  return { isSuccess, data, isLoading };
}

function useByeGuild(guildId) {
  return useMutation({
    mutationFn: () => byeGuild(guildId),
    onSuccess: () => {
      queryClient.invalidateQueries(['map']);
      queryClient.invalidateQueries(['guild', 'guildInfo', guildId]);
      queryClient.invalidateQueries(['guild', 'memberInfo', guildId]);
      queryClient.invalidateQueries(['guild', 'questInfo', guildId]);
    },
    onError: (error) => {
      throw error;
    },
  });
}

function useQuests() {
  const { isSuccess, data, isLoading } = useQuery({
    queryKey: ['guild', 'quests'],
    queryFn: getQuests,
    staleTime: 0,
    // staleTime: 1000 * 60 * 60 * 12,
    // cacheTime: 1000 * 60 * 60 * 24,
    refetchOnWindowFocus: true,
    refetchOnReconnect: true,
  });
  return { isSuccess, data, isLoading };
}

function useChooseQuest() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: ({ guildId, questId }) => chooseQuest(guildId, questId),
    onSuccess: (_, { guildId }) => {
      queryClient.invalidateQueries(['guild', 'guildInfo', guildId]);
      queryClient.invalidateQueries(['guild', 'memberInfo', guildId]);
      queryClient.invalidateQueries(['guild', 'questInfo', guildId]);
    },
    onError: (error) => {},
  });
}

function useMyInfo() {
  const { isSuccess, data, isLoading } = useQuery({
    queryKey: ['myInfo'],
    queryFn: getMyInfo,
    staleTime: 0,
    // staleTime: 1000 * 60 * 60 * 12,
    // cacheTime: 1000 * 60 * 60 * 24,
    refetchOnWindowFocus: true,
    refetchOnReconnect: true,
  });
  return { isSuccess, data, isLoading };
}

export {
  useGuildInfo,
  useMemberInfo,
  useQuestInfo,
  useByeGuild,
  useQuests,
  useChooseQuest,
  useMyInfo,
};
