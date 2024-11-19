import { create } from 'zustand';
import { totalQuest } from '@constants/quest';
import axiosInstance from '@api/axios';
import useEggModalDataStore from './eggModalDataStore';
import queryClient from '@api/queryClient';
const useHealthDataStore = create((set, get) => ({
  steps: 0,
  sleepHours: 0,
  dietData: {},
  completedQuestIds: [],

  // 헬스데이터를 업데이트하는 함수
  updateHealthData: (newSteps, newSleepHours) =>
    set({ steps: newSteps, sleepHours: newSleepHours }),

  updateDietData: (newDietData) => set({ dietData: newDietData }),

  // 퀘스트 완료 체크 함수
  checkQuestCompletion: async () => {
    const { steps, sleepHours, completedQuestIds, dietData } = get();

    const walkQuests = totalQuest.filter((quest) => quest.category === 'WALK');
    const sleepQuests = totalQuest.filter(
      (quest) => quest.category === 'SLEEP'
    );
    const dietQuests = totalQuest.filter((quest) => quest.category === 'DIET');

    const completedWalkQuestIds = walkQuests
      .filter((quest) => steps >= quest.goal)
      .map((quest) => quest.id);

    const completedSleepQuestIds = sleepQuests
      .filter((quest) => sleepHours >= quest.goal)
      .map((quest) => quest.id);

    const completedDietQuestIds = dietQuests
      .filter((quest) => {
        const enoughKeys = ['isCarboEnough', 'isProteinEnough'];

        // fat 조건 추가 (fat > 0인 경우에만 isFatEnough를 포함)
        if (dietData.fat > 0) {
          enoughKeys.push('isFatEnough');
        }

        // 충분 조건(true인 항목 수) 계산
        const enoughCount = enoughKeys.filter((key) => dietData[key]).length;

        // 목표 조건 충족 여부 반환
        return enoughCount >= quest.goal;
      })
      .map((quest) => quest.id);

    // 새로운 완료 퀘스트 ID 필터링
    const newCompletedIds = [
      ...completedWalkQuestIds,
      ...completedSleepQuestIds,
      ...completedDietQuestIds,
    ].filter((id) => !completedQuestIds.includes(id));

    if (newCompletedIds.length > 0) {
      set((state) => ({
        completedQuestIds: [...state.completedQuestIds, ...newCompletedIds],
      }));

      await get().completeQuestsSequentially(newCompletedIds);
    } else {
    }
  },

  // 퀘스트 완료 상태를 각각 백엔드로 전송
  sendQuestCompletion: async (questId, questType) => {
    try {
      // 퀘스트 타입에 따라 엔드포인트를 다르게 설정
      const endpoint =
        questType === 'personal'
          ? '/quests/personal/complete'
          : '/quests/guild/complete';

      // 설정한 엔드포인트로 요청 보냄
      const response = await axiosInstance.post(endpoint, {
        completeQuestId: questId,
      });
      const { setEggModalData } = useEggModalDataStore.getState();
      const { petStatus, petType, shouldShowModal } = response.data;
      if (shouldShowModal) {
        setEggModalData({
          shouldShowModal,
          newPetType: petType,
          newPetStatus: petStatus,
        });
      }
      queryClient.invalidateQueries(['mainPet']);
    } catch (error) {
      console.error(
        `${questType} 퀘스트 ID ${questId} 완료 전송 실패:`,
        error.response?.data || error.message
      );
    }
  },

  // 순차요청 처리
  completeQuestsSequentially: async (questIds) => {
    try {
      for (const id of questIds) {
        const quest = totalQuest.find((quest) => quest.id === id);
        await get().sendQuestCompletion(id, quest.type);
      }
    } catch (error) {
      console.error('순차요청 퀘스트 완료 전송 실패:', error.message);
    }
  },

  resetStore: () =>
    set({
      steps: 0,
      sleepHours: 0,
      dietData: {},
      completedQuestIds: [],
    }),
}));

export default useHealthDataStore;
