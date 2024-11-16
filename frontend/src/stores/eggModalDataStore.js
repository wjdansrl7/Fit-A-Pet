import { create } from 'zustand';

const useEggModalDataStore = create((set) => ({
  shouldShowModal: false,
  newPetType: null,
  newPetStatus: null,

  // 상태 업데이트 함수
  setEggModalData: ({ shouldShowModal, newPetType, newPetStatus }) =>
    set({ shouldShowModal, newPetType, newPetStatus }),
}));

export default useEggModalDataStore;
