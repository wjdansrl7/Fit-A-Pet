import React from 'react';
import { View, StyleSheet, TouchableOpacity, Image } from 'react-native';
import Modal from 'react-native-modal';
import CustomText from '@components/CustomText/CustomText';
import { colors } from '@src/constants';

const CustomModal = ({
  title, // 초록색 제목
  isVisible, // 모달 useState
  children, // 쓸 내용들
  animationIn = 'zoomIn',
  animationOut = 'zoomOut',
  wantClose = false, // 취소 아이콘 필요하면
  onClose, // 취소 효과 받을 수 있도록 설정 필요함
  modalStyle,
}) => {
  return (
    <Modal
      isVisible={isVisible}
      animationIn={animationIn}
      animationOut={animationOut}
      backdropColor="black"
      backdropOpacity={0.5}
    >
      <View style={[styles.modalContainer, modalStyle]}>
        <View style={styles.modalHeader}>
          <CustomText style={styles.headerText}>{title}</CustomText>
        </View>
        {wantClose && (
          <TouchableOpacity
            activeOpacity={0.5}
            onPress={onClose}
            style={styles.closeButton}
          >
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
    backgroundColor: colors.WHITE,
    paddingHorizontal: 20,
    paddingTop: 5,
    paddingBottom: 20,
    borderRadius: 20,
    alignItems: 'center',
    minHeight: '20%',
  },
  modalHeader: {
    position: 'absolute',
    top: -20,
    padding: 10,
    width: '70%',
    borderRadius: 10,
    backgroundColor: colors.MAIN_GREEN,
    alignItems: 'center',
  },
  headerText: {
    color: colors.WHITE,
  },
  closeButton: {
    position: 'absolute',
    top: 10,
    right: 20,
    alignSelf: 'flex-end',
  },
  modalBody: {
    marginTop: 50,
    paddingHorizontal: 20,
    paddingLeft: 30,
    alignItems: 'center',
  },
});

export default CustomModal;
