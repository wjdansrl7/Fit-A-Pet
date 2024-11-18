import React, { useState } from 'react';
import { View, StyleSheet, ScrollView, TouchableOpacity } from 'react-native';
import CustomModal from '@components/CustomModal/CustomModal';
import CustomButton from '@components/CustomButton/CustomButton';
import QuestGroupFrame from '@screens/quest/QuestGroupFrame';
import { colors } from '@src/constants';

const GuildQuestModal = ({ isVisible, onClose, onSetQuest, quests }) => {
  const [selectedCategory, setSelectedCategory] = useState('전체');
  const [selectedQuest, setSelectedQuest] = useState(null);
  const getFilteredQuests = () => {
    if (selectedCategory === '전체') {
      return quests;
    }
    return quests.filter((quest) => quest.questCategory === selectedCategory);
  };

  const filteredQuests = getFilteredQuests() || [];
  const handleQuestSelect = () => {
    if (selectedQuest) {
      onSetQuest(selectedQuest);
      onClose();
    }
  };

  return (
    <CustomModal
      title="퀘스트 목록"
      isVisible={isVisible}
      wantClose={true}
      onClose={onClose}
      modalStyle={styles.modalStyle}
    >
      <View style={styles.buttonContainer}>
        {['전체', 'WALK', 'SLEEP', 'DIET'].map((category) => (
          <CustomButton
            key={category}
            title={category}
            onPress={() => setSelectedCategory(category)}
            style={[
              styles.categoryButton,
              selectedCategory === category
                ? styles.selectedButton
                : styles.unselectedButton,
            ]}
          />
        ))}
      </View>

      <ScrollView
        showsVerticalScrollIndicator={false}
        contentContainerStyle={styles.scrollView}
      >
        {filteredQuests.map((quest) => (
          <TouchableOpacity
            activeOpacity={0.8}
            key={quest.questId}
            style={[
              styles.questFrameContainer,
              selectedQuest === quest
                ? styles.selectedQuest
                : styles.unselectedQuest,
            ]}
            onPress={() => setSelectedQuest(quest)}
          >
            <QuestGroupFrame quest={quest} />
          </TouchableOpacity>
        ))}
      </ScrollView>

      <View style={styles.selectButtonContainer}>
        <CustomButton
          title="퀘스트 선택"
          onPress={handleQuestSelect}
          style={styles.selectButton}
        />
      </View>
    </CustomModal>
  );
};

const styles = StyleSheet.create({
  modalStyle: {
    height: '90%',
  },
  buttonContainer: {
    flexDirection: 'row',
    justifyContent: 'space-around',
    marginBottom: 10,
    marginHorizontal: 10,
    gap: 10,
  },
  categoryButton: {
    paddingHorizontal: 10,
    paddingVertical: 5,
  },
  selectedButton: {},
  unselectedButton: {
    backgroundColor: colors.BACKGROUND_COLOR,
  },
  scrollView: {
    width: 270,
    gap: 12,
  },
  questFrameContainer: {
    borderRadius: 5,
  },
  selectedQuest: {
    borderColor: colors.MAIN_GREEN,
    borderWidth: 2,
  },
  unselectedQuest: {
    borderColor: 'transparent',
    borderWidth: 2,
  },
  selectButtonContainer: {
    marginTop: 10,
    alignItems: 'center',
  },
  selectButton: {
    width: '80%',
  },
});

export default GuildQuestModal;
