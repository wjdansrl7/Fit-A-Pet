import { queryClient } from '@src/api/queryClient';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { getMapInfo, createGuild, joinGuild } from '@api/map';

function useMapInfo() {
  const { isSuccess, data, isLoading, refetch } = useQuery({
    queryKey: ['map'],
    queryFn: getMapInfo,
    staleTime: 0,
    // staleTime: 1000 * 60 * 30,
    // cacheTime: 1000 * 60 * 60,
    refetchOnWindowFocus: true,
    refetchOnReconnect: true,
  });

  return { isSuccess, data, isLoading, refetch };
}

function useCreateGuild() {
  const queryClient = useQueryClient(); // useQueryClient로 queryClient 접근

  return useMutation({
    mutationFn: async (guildCreateInfo) => {
      try {
        await createGuild(guildCreateInfo);
        queryClient.invalidateQueries(['map']); // 성공 시 map 쿼리 무효화
        return 'success';
      } catch (error) {
        if (error.response && error.response.status === 406) {
          return 'duplicate'; // 중복 오류 반환
        } else {
          return 'error'; // 일반 오류 반환
        }
      }
    },
  });
}

function useJoinGuild() {
  const queryClient = useQueryClient(); // useQueryClient로 queryClient 접근
  return useMutation({
    mutationFn: async (guildJoinInfo) => {
      try {
        await joinGuild(guildJoinInfo);
        queryClient.invalidateQueries(['map']); // 성공 시 map 쿼리 무효화
        return 'success';
      } catch (error) {
        if (error.response && error.response.status === 406) {
          return 'full';
        } else {
          return 'invalidCode';
        }
      }
    },
  });
}

export { useMapInfo, useCreateGuild, useJoinGuild };
