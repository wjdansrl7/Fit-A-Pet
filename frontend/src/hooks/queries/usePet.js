import { useQuery, useQueryClient, useMutation } from '@tanstack/react-query';
import axiosInstance from '@api/axios';

const queryClient = useQueryClient();

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
  const mutation = useMutation({
    mutationFn: updateNickname,
    // onSuccess: (data) => {
    //   console.log('Updated nickname:', data);
    // },
    // onError: (error) => {
    //   console.error(error);
    // },
  });
  return mutation;
};
