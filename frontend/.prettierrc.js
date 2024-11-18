module.exports = {
  singleQuote: true, // 문자열에 작은따옴표 사용
  printWidth: 80, // 한 줄에 최대 80자까지만 허용 (자동 줄바꿈)
  tabWidth: 2, // 들여쓰기 시 탭 대신 공백 2칸 사용
  useTabs: false, // 들여쓰기에 탭 대신 공백 사용
  semi: true, // 코드 줄 끝에 세미콜론 추가
  quoteProps: 'as-needed', // 객체 속성에 필요한 경우에만 따옴표 사용
  jsxSingleQuote: false, // JSX에서 큰따옴표 사용 (작은따옴표 대신)
  trailingComma: 'es5', // 객체나 배열의 마지막 요소 뒤에 쉼표 추가 (ES5 호환)
  bracketSpacing: true, // 객체 리터럴에서 중괄호 안쪽에 공백 추가 ({ foo: 'bar' })
  bracketSameLine: false, // 닫는 JSX 태그를 다음 줄로 이동
  arrowParens: 'always', // 화살표 함수 매개변수에 항상 괄호 사용
  endOfLine: 'lf', // 줄바꿈을 Unix 방식(LF)으로 설정
  proseWrap: 'preserve', // 마크다운에서 기존 줄바꿈 유지
};
