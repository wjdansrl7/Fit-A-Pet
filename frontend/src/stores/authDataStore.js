import { create } from 'zustand';

const useAuthDataStore = create((set) => ({
  loginStatus: false,

  setAuthData: (updatedLoginStatus) => set({ loginStatus: updatedLoginStatus }),

  clearAuthData: () => set({ loginStatus: false }),
}));

export default useAuthDataStore;
