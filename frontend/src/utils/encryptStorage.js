import EncryptedStorage from 'react-native-encrypted-storage';

const setEncryptStorage = async (key, data) => {
  await EncryptedStorage.setItem(key, JSON.stringify(data));
};

const getEncryptStorage = async (key) => {
  const storedData = await EncryptedStorage.getItem(key);

  return storedData ? JSON.parse(storedData) : null;
};

const removeEncryptStorage = async (key) => {
  const data = await getEncryptStorage(key);

  if (data) {
    await EncryptedStorage.removeItem(key);
  }
};

export { setEncryptStorage, getEncryptStorage, removeEncryptStorage };
