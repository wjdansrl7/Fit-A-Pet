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
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async (guildCreateInfo) => {
      try {
        await createGuild(guildCreateInfo);
        queryClient.invalidateQueries(['map']);
        return 'success';
      } catch (error) {
        if (error.response && error.response.status === 406) {
          return 'duplicate';
        } else {
          return 'error';
        }
      }
    },
  });
}

function useJoinGuild() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async (guildJoinInfo) => {
      try {
        await joinGuild(guildJoinInfo);
        queryClient.invalidateQueries(['map']);
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
