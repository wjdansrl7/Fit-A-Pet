module.exports = {
  presets: ['module:@react-native/babel-preset'],  plugins: [
    [
      'module-resolver',
      {
        root: ['./src'], // src 폴더를 루트로 지정
        alias: {
          '@components': './src/components',
          '@screens': './src/screens',
          '@assets': './src/assets',
          '@api': './src/api',
          '@constants': './src/constants',
          '@hooks' : './src/hooks',
          '@util': './src/utils',
          '@src': './src' 
        },
      },
    ],
    'react-native-reanimated/plugin',
  ],
};
