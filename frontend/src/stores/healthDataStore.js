import { create } from 'zustand';

const useHealthDataStore = create((set) => ({
  steps: 0,
  sleepHours: 0,
  updateHealthData: (newSteps, newSleepHours) =>
    set({ steps: newSteps, sleepHours: newSleepHours }),
  checkQuestCompletion: (stepGoal, sleepGoal) => {
    const { steps, sleepHours } = get();
    return {
      stepsCompleted: steps >= stepGoal,
      sleepCompleted: sleepHours >= sleepGoal,
    };
  },
}));

export default useHealthDataStore;
