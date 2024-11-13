import AnimatedSprite from '@components/AnimatedSprite/AnimatedSprite';
import spriteData from '@assets/pets/lion_3_sprite.json';
import spriteImage from '@assets/pets/lion_3_sprite.png';

const frames = Object.values(spriteData.frames).map((frame) => ({
  x: frame.frame.x,
  y: frame.frame.y,
  w: frame.frame.w,
  h: frame.frame.h,
  duration: frame.duration,
}));

const animations = {
  walk: [0, 1, 0],
  // 적당히 조절 해야할듯, 아님 동물마다 저장을 하던가
};

<AnimatedSprite
  source={spriteImage} // 소스
  spriteSheetWidth={256} // 실제 스프라이트 크기
  spriteSheetHeight={768}
  width={70} // 프레임의 너비
  height={70} // 프레임의 높이
  frames={frames} // 이건 거의 디폴트임
  animations={animations} // 이걸로 움직이는 화면 조절
  defaultAnimationName="walk" // 이건 해놔야 연동됨
  inLoop={true} // 루프
  autoPlay={true} // 자동시작
  frameRate={5} // 움직이는 속도
/>;
