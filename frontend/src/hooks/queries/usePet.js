import { useQuery, useQueryClient, useMutation } from '@tanstack/react-query';
import axiosInstance from '@api/axios';
import { Alert } from 'react-native';

const fetchPetAlbumList = async () => {
  const response = await axiosInstance.get('/petbooks');
  return response.data;
};

export const usePetAlbumList = () => {
  const { data, isError, isLoading } = useQuery({
    queryKey: ['petAlbumList'],
    queryFn: fetchPetAlbumList,
  });
  return { data, isError, isLoading };
};

const fetchMainPetInfo = async () => {
  const response = await axiosInstance.get('/petbooks/main');
  return response.data;
};

export const useMainPetInfo = () => {
  const { data, isError, isLoading } = useQuery({
    queryKey: ['mainPet'],
    queryFn: fetchMainPetInfo,
  });
  return { data, isError, isLoading };
};

const updateNickname = async ({ petBookId, newNickname }) => {
  const response = await axiosInstance.post(`/petbooks/${petBookId}/nickname`, {
    petNickname: newNickname,
  });
  return response.data;
};

export const useUpdateNickname = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: updateNickname,
    onSuccess: () => {
      queryClient.invalidateQueries(['mainPetInfo']);
    },
    onError: (error) => {
      Alert.alert('Error', '닉네임 변경에 실패했습니다.');
      console.error(error);
    },
  });
};

const updateMain = async (petBookId) => {
  const response = await axiosInstance.post(`/petbooks/main/${petBookId}`);
  return response.data;
};
