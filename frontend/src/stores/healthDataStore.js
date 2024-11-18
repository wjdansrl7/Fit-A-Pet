import { create } from 'zustand';
import { personalQuest, totalQuest } from '@constants/quest';
import axios from 'axios';
import axiosInstance from '@api/axios';
import useEggModalDataStore from './eggModalDataStore';
import queryClient from '@api/queryClient';
const useHealthDataStore = create((set, get) => ({
  steps: 0,
  sleepHours: 0,

  dailyDiet: null,

  completedQuestIds: [], //이미 달성한 퀘스트ID 저장

  // 헬스데이터를 업데이트하는 함수
  updateHealthData: (newSteps, newSleepHours) =>
    set({ steps: newSteps, sleepHours: newSleepHours }),

  // 퀘스트 완료 체크 함수
  checkQuestCompletion: async () => {
    const { steps, sleepHours, completedQuestIds } = get();

    // 걷기와 수면 퀘스트를 분리하여 완료 여부 체크
    const walkQuests = totalQuest.filter((quest) => quest.category === 'WALK');
    const sleepQuests = totalQuest.filter(
      (quest) => quest.category === 'SLEEP'
    );

    const completedWalkQuestIds = walkQuests
      .filter((quest) => steps >= quest.goal)
      .map((quest) => quest.id);

    const completedSleepQuestIds = sleepQuests
      .filter((quest) => sleepHours >= quest.goal)
      .map((quest) => quest.id);

    // 새로운 완료 퀘스트 ID 필터링
    const newCompletedIds = [
      ...completedWalkQuestIds,
      ...completedSleepQuestIds,
    ].filter((id) => !completedQuestIds.includes(id));

    if (newCompletedIds.length > 0) {
      set((state) => ({
        completedQuestIds: [...state.completedQuestIds, ...newCompletedIds],
      }));
      await get().completeQuestsSequentially(newCompletedIds);
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
      console.log(`퀘스트 ID ${questId} 완료 전송 성공:`, response.data);
      const { setEggModalData } = useEggModalDataStore.getState();
      const { petStatus, petType, shouldShowModal } = response.data;
      console.log(shouldShowModal, '받음?');
      console.log(response.data, '이거');
      if (shouldShowModal) {
        console.log('알받기,성공');
        console.log('shouldShowModal:', shouldShowModal);
        console.log('newPetType:', petType);
        console.log('newPetStatus:', petStatus);
        setEggModalData({
          shouldShowModal,
          newPetType: petType,
          newPetStatus: petStatus,
        });
        queryClient.invalidateQueries(['mainPet']);
      }
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
        await get().sendQuestCompletion(id, quest.type); // 순차적으로 요청을 보냄
        console.log(`순차요청 ${quest.type} 퀘스트 ID ${id} 완료 전송 성공`);
      }
    } catch (error) {
      console.error('순차요청 퀘스트 완료 전송 실패:', error.message);
    }
  },

  // 개인 퀘스트만 가능한 코드
  // sendQuestCompletion: async (questId) => {
  //   try {
  //     // 설정한 엔드포인트로 요청 보냄
  //     const response = await axiosInstance.post('/quests/personal/complete', {
  //       completeQuestId: questId,
  //     });
  //     console.log(`퀘스트 ID ${questId} 완료 전송 성공:`, response.data);
  //     const { setEggModalData } = useEggModalDataStore.getState();
  //     const { petStatus, petType, shouldShowModal } = response.data;

  //     if (shouldShowModal) {
  //       setEggModalData({
  //         shouldShowModal,
  //         newPetType: petType,
  //         newPetStatus: petStatus,
  //       });
  //     }
  //   } catch (error) {
  //     console.error(
  //       `퀘스트 ID ${questId} 완료 전송 실패:`,
  //       error.response?.data || error.message
  //     );
  //   }
  // },
  // // 순차요청 처리
  // completeQuestsSequentially: async (questIds) => {
  //   try {
  //     for (const id of questIds) {
  //       await get().sendQuestCompletion(id); // 순차적으로 요청을 보냄
  //       console.log(`순차요청 퀘스트 ID ${id} 완료 전송 성공`);
  //     }
  //   } catch (error) {
  //     console.error('순차요청 퀘스트 완료 전송 실패:', error.message);
  //   }
  // },

  //
}));

export default useHealthDataStore;
