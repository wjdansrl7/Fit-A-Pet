import { create } from 'zustand';

const useEggModalDataStore = create((set) => ({
  shouldShowModal: false,
  newPetType: null,
  newPetStatus: null,

  setEggModalData: ({ shouldShowModal, newPetType, newPetStatus }) =>
    set({ shouldShowModal, newPetType, newPetStatus }),
}));

export default useEggModalDataStore;
