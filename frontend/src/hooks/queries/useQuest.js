import { useQuery } from '@tanstack/react-query';
// import { useQuery, useMutation } from '@tanstack/react-query';

import { getQuests } from '@api/questApi';

export const useGetQuests = () => {
  const { isSuccess, data, isError } = useQuery({
    queryKey: ['getQuests'],
    queryFn: getQuests,
    refetchOnReconnect: true,
    refetchIntervalInBackground: true,
  });

  return { data, isSuccess, isError };
};
