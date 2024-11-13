import React, { useEffect, useState } from 'react';
import { Text, View } from 'react-native';
import {
  initialize,
  requestPermission,
  readRecords,
} from 'react-native-health-connect';

const HealthData = () => {
  const [records, setRecords] = useState([]);

  useEffect(() => {
    const fetchHealthData = async () => {
      try {
        // 1. 클라이언트 초기화
        const isInitialized = await initialize();
        if (!isInitialized) {
          console.log('Health Connect 초기화에 실패했습니다.');
          return;
        }

        // 2. 권한 요청
        const grantedPermissions = await requestPermission([
          { accessType: 'read', recordType: 'Steps' },
        ]);

        if (!grantedPermissions) {
          console.log('권한이 거부되었습니다.');
          return;
        }

        // 3. 데이터 읽기
        const { records } = await readRecords('Steps', {
          timeRangeFilter: {
            operator: 'between',
            startTime: '2024-11-10T12:00:00.405Z',
            endTime: '2024-11-15T23:53:15.405Z',
          },
        });

        setRecords(records);
        // console.log('Health Connect 데이터:', records);
      } catch (error) {
        console.error('Health Connect 데이터 가져오기 오류:', error);
      }
    };

    fetchHealthData();
  }, []);

  return (
    <View style={{ padding: 20 }}>
      <Text>Steps:</Text>
      {records.length > 0 ? (
        records.map((record, index) => (
          <Text key={index}>
            {`시작 시간: ${record.startTime}, 종료 시간: ${record.endTime}, 걸음수: ${record.count}`}
          </Text>
        ))
      ) : (
        <Text>데이터를 불러오는 중...</Text>
      )}
    </View>
  );
};

export default HealthData;
