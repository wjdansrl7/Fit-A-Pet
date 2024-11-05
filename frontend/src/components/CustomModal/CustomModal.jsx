import React from 'react';
import { View, StyleSheet, TouchableOpacity, Image } from 'react-native';
import Modal from 'react-native-modal';
import CustomText from '@components/CustomText/CustomText';

const CustomModal = ({
  title, // 초록색 제목
  isVisible, // 모달 useState
  children, // 쓸 내용들
  animationIn = 'zoomIn',
  animationOut = 'zoomOut',
  wantClose = false, // 취소 아이콘 필요하면
  onClose, // 취소 효과 받을 수 있도록 설정 필요함
}) => {
  return (
    <Modal
      isVisible={isVisible}
      animationIn={animationIn}
      animationOut={animationOut}
      backdropColor="black"
      backdropOpacity={0.5}
    >
      <View style={styles.modalContainer}>
        <View style={styles.modalHeader}>
          <CustomText style={styles.headerText}>{title}</CustomText>
        </View>
        {wantClose && (
          <TouchableOpacity onPress={onClose} style={styles.closeButton}>
            <CustomText style={{ fontSize: 30, color: 'black' }}>⨉</CustomText>
          </TouchableOpacity>
        )}
        <View style={styles.modalBody}>{children}</View>
      </View>
    </Modal>
  );
};

const styles = StyleSheet.create({
  modalContainer: {
    width: '100%',
    backgroundColor: 'white',
    paddingHorizontal: 20,
    paddingTop: 5,
    paddingBottom: 20,
    borderRadius: 20,
    borderWidth: 2,
    borderColor: 'transparent',
    justifyContent: 'center',
    alignItems: 'center',
    minHeight: '20%',
  },
  modalHeader: {
    position: 'absolute',
    top: -20,
    padding: 10,
    width: '70%',
    borderRadius: 10,
    backgroundColor: 'green',
    alignItems: 'center',
  },
  headerText: {
    color: 'white',
  },
  closeButton: {
    alignSelf: 'flex-end',
  },
  modalBody: {
    marginTop: 5,
    paddingHorizontal: 20,
    justifyContent: 'center',
    alignItems: 'center',
  },
});

export default CustomModal;
