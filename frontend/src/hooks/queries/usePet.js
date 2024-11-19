import { useQuery, useQueryClient, useMutation } from '@tanstack/react-query';
import axiosInstance from '@api/axios';
import { Alert } from 'react-native';

// 도감 리스트 가져오기
const fetchPetAlbumList = async () => {
  const response = await axiosInstance.get('/petbooks');
  return response.data;
};
export const usePetAlbumList = () => {
  const { data, error, isLoading, isError } = useQuery({
    queryKey: ['petAlbumList'],
    queryFn: fetchPetAlbumList,
  });
  return { data, error, isLoading, isError };
};

// 메인 펫 정보 가져오기
const fetchMainPetInfo = async () => {
  const response = await axiosInstance.get('/petbooks/main');
  return response.data;
};
export const useMainPetInfo = () => {
  const { data, isError, isLoading, error, refetch } = useQuery({
    queryKey: ['mainPet'],
    queryFn: fetchMainPetInfo,
  });
  return { data, isError, isLoading, error, refetch };
};

// 메인 펫 닉네임 변경
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
      console.error(error);
    },
  });
};

// 메인펫 변경
const updateMain = async (petBookId) => {
  const response = await axiosInstance.post(`/petbooks/main/${petBookId}`);
  return response.data;
};
export const useUpdateMain = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: updateMain,
    onSuccess: () => {
      queryClient.invalidateQueries(['mainPetInfo']);
    },
    onError: (error) => {
      console.error(error);
    },
  });
};
