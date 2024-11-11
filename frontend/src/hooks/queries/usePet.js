import { useQuery } from '@tanstack/react-query';
import axiosInstance from '@api/axios';

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
