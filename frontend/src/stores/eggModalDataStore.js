import { create } from 'zustand';

const useEggModalDataStore = create((set) => ({
  shouldShowModal: false,
  newPetType: null,
  newPetStatus: null,

  // 상태 업데이트 함수
  setEggModalData: ({ shouldShowModal, newPetType, newPetStatus }) =>
    set((state) => ({
      ...state, // 기존 상태 유지
      shouldShowModal: shouldShowModal ?? state.shouldShowModal,
      newPetType: newPetType ?? state.newPetType,
      newPetStatus: newPetStatus ?? state.newPetStatus,
    })),
}));

export default useEggModalDataStore;
