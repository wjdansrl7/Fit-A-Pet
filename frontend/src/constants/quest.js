const personalQuest = [
  { id: 5, category: 'WALK', goal: 3000, title: '3000보 걷기' },
  { id: 11, category: 'WALK', goal: 5000, title: '5000보 걷기' },
  { id: 17, category: 'WALK', goal: 10000, title: '10000보 걷기' },
  { id: 4, category: 'SLEEP', goal: 6, title: '6시간 자기' },
  { id: 10, category: 'SLEEP', goal: 7, title: '7시간 자기' },
  { id: 16, category: 'SLEEP', goal: 8, title: '8시간 자기' },
];

// 퀘스트 통합 중
const totalQuest = [
  // 개인 퀘스트
  { id: 5, category: 'WALK', goal: 3000, type: 'personal' },
  { id: 11, category: 'WALK', goal: 5000, type: 'personal' },
  { id: 17, category: 'WALK', goal: 10000, type: 'personal' },
  { id: 4, category: 'SLEEP', goal: 6, type: 'personal' },
  { id: 10, category: 'SLEEP', goal: 7, type: 'personal' },
  { id: 16, category: 'SLEEP', goal: 8, type: 'personal' },
  { id: 6, category: 'DIET', goal: 1, type: 'personal' },
  { id: 12, category: 'DIET', goal: 2, type: 'personal' },
  { id: 18, category: 'DIET', goal: 3, type: 'personal' },
  // 길드 퀘스트
  { id: 2, category: 'WALK', goal: 3000, type: 'guild' },
  { id: 8, category: 'WALK', goal: 5000, type: 'guild' },
  { id: 14, category: 'WALK', goal: 10000, type: 'guild' },
  { id: 1, category: 'SLEEP', goal: 6, type: 'guild' },
  { id: 7, category: 'SLEEP', goal: 7, type: 'guild' },
  { id: 13, category: 'SLEEP', goal: 8, type: 'guild' },
  { id: 3, category: 'DIET', goal: 1, type: 'guild' },
  { id: 9, category: 'DIET', goal: 2, type: 'guild' },
  { id: 15, category: 'DIET', goal: 3, type: 'guild' },
];

export { personalQuest, totalQuest };
