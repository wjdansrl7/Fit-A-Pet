import React from 'react';
import CustomModal from '@components/CustomModal/CustomModal';
import CustomButton from '@components/CustomButton/CustomButton';
import CustomText from '@components/CustomText/CustomText';

const GroupGoalModal = ({ isVisible, onClose, onSetGoal }) => {
  return (
    <CustomModal
      title="목표 설정"
      isVisible={isVisible}
      wantClose={true}
      onClose={onClose}
    >
      <CustomText>목표를 설정해주세요!</CustomText>
      <CustomButton title="설정 완료" onPress={onSetGoal} />
    </CustomModal>
  );
};

export default GroupGoalModal;
