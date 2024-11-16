import { useState } from 'react';
import {
  initialize,
  requestPermission,
  readRecords,
} from 'react-native-health-connect';

// 오늘 날짜를 설정
function getTodayTimeRange() {
  // 오늘 날짜
  const today = new Date();

  // 임의로 11월 12일을 오늘로 설정
  // const today = new Date(2024, 10, 13);

  const yesterDay = new Date(today.setDate(today.getDate() - 1));

  // 오늘의 시작 시각 (00:00:00.000)
  const startTime = new Date(today.setHours(0, 0, 0, 0)).toISOString();
  // 오늘의 끝 시각 (23:59:59.999)
  const endTime = new Date(today.setHours(23, 59, 59, 999)).toISOString();

  // 수면조회 시작 시간 : 전날 오후 6시
  const sleepStartTime = new Date(
    yesterDay.setHours(18, 0, 0, 0)
  ).toISOString();

  return { startTime, endTime, sleepStartTime };
}

// KST 변경 함수
function convertToKST(utcTime) {
  const date = new Date(utcTime);
  return date.toLocaleString('ko-KR', { timeZone: 'Asia/Seoul' });
}

// 시간 차이를 계산하는 함수
function calculateDuration(start, end) {
  const startDate = new Date(start);
  const endDate = new Date(end);

  // 시간 차이를 밀리초로 계산
  const diffInMs = endDate - startDate;

  // 밀리초를 시간 단위로 변환
  const diffInHours = diffInMs / (1000 * 60 * 60);

  return diffInHours;
}

// 걷기 데이터 가져오기
const fetchStepData = async (startTime, endTime) => {
  try {
    // const stepResponse = await readRecords('Steps', {
    //   timeRangeFilter: {
    //     operator: 'between',
    //     startTime: startTime,
    //     endTime: endTime,
    //   },
    // });
    // const [stepRecords] = stepResponse.records;

    const stepRecords = {
      count: 10000,
      endTime: '2024-11-13T14:59:59.999Z',
      metadata: {
        clientRecordId: null,
        clientRecordVersion: 0,
        dataOrigin: 'com.sec.android.app.shealth',
        device: null,
        id: '616c53db-35b7-41c1-8056-bda65a9660fc',
        lastModifiedTime: '2024-11-13T00:13:25.184Z',
        recordingMethod: 0,
      },
      startTime: '2024-11-12T15:00:00Z',
    };

    return stepRecords ? stepRecords.count : 0; // 기록이 없을 경우 0 반환
  } catch (error) {
    console.error('Steps 데이터 가져오기 오류:', error);
    return 0; // 오류 시 0 반환
  }
};

const fetchSleepData = async (sleepStartTime, endTime) => {
  try {
    // const sleepResponse = await readRecords('SleepSession', {
    //   timeRangeFilter: {
    //     operator: 'between',
    //     startTime: sleepStartTime,
    //     endTime: endTime,
    //   },
    // });
    // const [sleepRecords] = sleepResponse.records;

    const sleepRecords = {
      endTime: '2024-11-13T19:30:00Z',
      metadata: {
        clientRecordId: null,
        clientRecordVersion: 0,
        dataOrigin: 'com.sec.android.app.shealth',
        device: null,
        id: '14529272-7986-4549-83dd-64755689de8c',
        lastModifiedTime: '2024-11-14T04:00:57.468Z',
        recordingMethod: 0,
      },
      notes: null,
      stages: [],
      startTime: '2024-11-13T13:00:00Z',
      title: null,
    };

    if (sleepRecords) {
      const duration = calculateDuration(
        sleepRecords.startTime,
        sleepRecords.endTime
      );
      return duration; // 수면 시간 반환
    } else {
      return 0; // 기록이 없을 경우 0 반환
    }
  } catch (error) {
    console.error('Sleep 데이터 가져오기 오류:', error);
    return 0; // 오류 시 0 반환
  }
};

// 헬스 데이터 store에 저장
export const fetchHealthData = async () => {
  try {
    const { startTime, endTime, sleepStartTime } = getTodayTimeRange();

    // 1. 클라이언트 초기화
    const isInitialized = await initialize();
    if (!isInitialized) {
      console.log('Health Connect 초기화에 실패했습니다.');
      return;
    }

    // 2. 권한 요청
    const grantedPermissions = await requestPermission([
      { accessType: 'read', recordType: 'Steps' },
      { accessType: 'read', recordType: 'SleepSession' },
    ]);

    if (!grantedPermissions) {
      console.log('권한이 거부되었습니다.');
      return;
    }

    // 3. 데이터 읽기
    const steps = await fetchStepData(startTime, endTime);
    const sleepHours = await fetchSleepData(sleepStartTime, endTime);

    return { steps, sleepHours };
  } catch (error) {
    console.error('Health Connect 데이터 가져오기 오류:', error);
    return {
      steps: 0,
      sleepHours: 0,
    };
  }
};
