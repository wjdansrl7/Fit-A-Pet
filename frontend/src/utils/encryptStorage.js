import EncryptStorage from 'react-native-encrypted-storage';

const setEncryptStorage = async () => {
  await EncryptStorage.setItem(key, JSON.stringify(data));
};

const getEncryptStorage = async () => {
  const storedData = await EncryptStorage.getItem(key);

  return storedData ? JSON.parse(storedData) : null;
};

const removeEncryptStorage = async () => {
  const data = await getEncryptStorage(key);

  if (data) {
    await EncryptStorage.removeItem(key);
  }
};

export { setEncryptStorage, getEncryptStorage, removeEncryptStorage };
