import { queryClient } from '@src/api/queryClient';
import { useQuery, useMutation } from '@tanstack/react-query';
import { getMapInfo, createGuild, joinGuild } from '@api/map';

function useMapInfo() {
  const { isSuccess, data, isLoading } = useQuery({
    queryKey: ['map'],
    queryFn: getMapInfo,
    staleTime: 1000 * 60 * 30,
    cacheTime: 1000 * 60 * 60,
    refetchOnWindowFocus: true,
    refetchOnReconnect: true,
  });

  return { isSuccess, data, isLoading };
}

function useCreateGuild(setErrorState) {
  return useMutation({
    mutationFn: createGuild,
    onSuccess: () => {
      queryClient.invalidateQueries(['map']);
    },
    onError: (error) => {
      if (error.response && error.response.status === 406) {
        setErrorState('duplicate'); // 중복 에러
      } else {
        setErrorState('error'); // 일반 에러
      }
      console.error('Guild creation failed:', error);
    },
  });
}

function useJoinGuild(setErrorState) {
  return useMutation({
    mutationFn: joinGuild,
    onSuccess: () => {
      queryClient.invalidateQueries(['map']);
    },
    onError: (error) => {
      if (error.response && error.response.status === 406) {
        setErrorState('invalidCode'); // 초대 에러
      } else {
        setErrorState('full'); // 풀방 에러
      }
      console.error('Guild join failed:', error);
    },
  });
}

export { useMapInfo, useCreateGuild, useJoinGuild };
