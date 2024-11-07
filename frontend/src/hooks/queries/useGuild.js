import { queryClient } from '@src/api/queryClient';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import {
  getGuildInfo,
  getMemberInfo,
  getQuestInfo,
  getEnteringCode,
  byeGuild,
  getQuests,
  chooseQuest,
} from '@api/guild';

function useGuildInfo(guildId) {
  const { isSuccess, data, isLoading } = useQuery({
    queryKey: ['guild', 'guildInfo', guildId],
    queryFn: () => getGuildInfo(guildId),
    staleTime: 1000 * 60 * 30,
    cacheTime: 1000 * 60 * 60,
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
    staleTime: 1000 * 60 * 5,
    cacheTime: 1000 * 60 * 10,
    refetchOnWindowFocus: true,
    refetchOnReconnect: true,
    refetchInterval: 1000 * 60 * 5,
  });
  return { isSuccess, data, isLoading };
}

function useEnteringCode(guildId) {
  const { isSuccess, data, isLoading } = useQuery({
    queryKey: ['guild', 'enteringCode', guildId],
    queryFn: () => getEnteringCode(guildId),
    staleTime: 0,
    cacheTime: 0,
    refetchOnWindowFocus: true,
    refetchOnReconnect: true,
  });
  return { isSuccess, data, isLoading };
}

function useByeGuild(guildId, setErrorState) {
  return useMutation({
    mutationFn: () => byeGuild(guildId),
    onSuccess: () => {
      queryClient.invalidateQueries(['map']);
    },
    onError: (error) => {
      console.error('Guild creation failed:', error);
    },
  });
}

function useQuests() {
  const { isSuccess, data, isLoading } = useQuery({
    queryKey: ['guild', 'quests'],
    queryFn: getQuests,
    staleTime: 1000 * 60 * 60 * 12,
    cacheTime: 1000 * 60 * 60 * 24,
    refetchOnWindowFocus: true,
    refetchOnReconnect: true,
  });
  return { isSuccess, data, isLoading };
}

function useChooseQuest(guildId, questId) {
  return useMutation({
    mutationFn: () => chooseQuest(guildId, questId),
    onSuccess: () => {
      queryClient.invalidateQueries(['guild', 'guildInfo', guildId]);
      queryClient.invalidateQueries(['guild', 'memberInfo', guildId]);
      queryClient.invalidateQueries(['guild', 'questInfo', guildId]);
    },
    onError: (error) => {
      console.error('Guild creation failed:', error);
    },
  });
}

export {
  useGuildInfo,
  useMemberInfo,
  useQuestInfo,
  useEnteringCode,
  useByeGuild,
  useQuests,
  useChooseQuest,
};
