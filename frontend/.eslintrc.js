module.exports = {
  root: true,
  env: { browser: true, es2020: true, 'react-native/react-native': true },
  extends: [
    'eslint:recommended',
    'plugin:react/recommended',
    'plugin:react/jsx-runtime',
    'plugin:react-hooks/recommended',
    'plugin:react-native/all',
    'prettier',
  ],
  ignorePatterns: ['dist', '.eslintrc.cjs', 'node_modules'],
  parserOptions: { ecmaVersion: 'latest', sourceType: 'module' },
  settings: { react: { version: '18.2' } },
  plugins: ['react-refresh', 'prettier', 'react-native'],
  rules: {
    'react/jsx-no-target-blank': 'off',
    'react-refresh/only-export-components': [
      'warn',
      { allowConstantExport: true },
    ],
    'no-unused-vars': 'off',
    'react/prop-types': 'off',
    'prettier/prettier': [
      'error',
      {
        bracketSpacing: true, // 중괄호 안 공백 무조건 유지
      },
    ],
    'react-native/split-platform-components': 'warn',
  },
};
