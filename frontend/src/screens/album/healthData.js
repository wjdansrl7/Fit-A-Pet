import { useState } from 'react';
import {
  initialize,
  requestPermission,
  readRecords,
} from 'react-native-health-connect';

function getTodayTimeRange() {
  // 오늘 날짜
  // const today = new Date();

  // 임의로 11월 12일을 오늘로 설정
  const today = new Date(2024, 10, 13);

  // 오늘의 시작 시각 (00:00:00.000)
  const startTime = new Date(today.setHours(0, 0, 0, 0)).toISOString();
  // 오늘의 끝 시각 (23:59:59.999)
  const endTime = new Date(today.setHours(23, 59, 59, 999)).toISOString();
  return { startTime, endTime };
}

export const fetchHealthData = async () => {
  try {
    const { startTime, endTime } = getTodayTimeRange();

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

    // 3. 데이터 읽기 - 걷기 데이터
    const stepResponse = await readRecords('Steps', {
      timeRangeFilter: {
        operator: 'between',
        startTime: startTime,
        endTime: endTime,
      },
    });

    // const sleepResponse = await readRecords('SleepSession', {
    //   timeRangeFilter: {
    //     operator: 'between',
    //     startTime: '2024-11-09T20:00:00Z',
    //     endTime: '2024-11-11T23:59:59Z',
    //     startZoneOffset: '-8',
    //   },
    // });

    const [stepRecords] = stepResponse.records;
    // const [sleepRecords] = sleepResponse.records;

    // console.log('Health Connect 데이터:', stepRecords);
    // console.log('원본', sleepResponse);
    // console.log('Health Connect 데이터:', sleepRecords);
    return {
      steps: stepRecords.count,
      sleepHours: 8,
    };
  } catch (error) {
    console.error('Health Connect 데이터 가져오기 오류:', error);
    return {
      steps: 0,
      sleepHours: 0,
    };
  }
};
