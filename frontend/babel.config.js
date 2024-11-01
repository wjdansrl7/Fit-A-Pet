module.exports = {
  presets: ['module:metro-react-native-babel-preset'],
  plugins: [
    [
      'module-resolver',
      {
        root: ['./src'], // src 폴더를 루트로 지정
        alias: {
          '@components': './src/components',
          '@screens': './src/screens',
          '@assets': './src/assets',
          '@src': './src' // src 폴더를 @src로 매핑
        },
      },
    ],
  ],
};
