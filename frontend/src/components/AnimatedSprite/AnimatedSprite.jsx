import React, {
  forwardRef,
  useCallback,
  useEffect,
  useImperativeHandle,
} from 'react';
import { View, StyleSheet, Image } from 'react-native';
import Animated, {
  useSharedValue,
  useAnimatedStyle,
  withTiming,
  withRepeat,
  Easing,
} from 'react-native-reanimated';

const AnimatedImage = Animated.createAnimatedComponent(Image);

const AnimatedSprite = forwardRef((props, ref) => {
  const {
    source,
    spriteSheetWidth,
    spriteSheetHeight,
    frameRate = 10,
    width,
    height,
    frames,
    inLoop = true,
    autoPlay = true,
    animations,
    defaultAnimationName,
  } = props;

  const currentAnimationName = useSharedValue(defaultAnimationName);
  const frameIndex = useSharedValue(0);

  const ToggleAnimation = useCallback(
    (animationName, loop = false, customFrameRate = 10) => {
      if (!animations[animationName]) {
        console.warn(`Invalid animation name: ${animationName}`);
        return;
      }
      currentAnimationName.set(animationName);
      frameIndex.set(0);
      const selectedFramesIndices = animations[animationName] ?? [];
      frameIndex.value = withRepeat(
        withTiming(selectedFramesIndices.length - 1, {
          duration: (1000 / customFrameRate) * selectedFramesIndices.length,
          easing: Easing.linear,
        }),
        loop ? -1 : 1,
        false
      );
    },
    [animations, currentAnimationName, frameIndex]
  );

  useImperativeHandle(
    ref,
    () => ({
      startAnimation: (animationName, loop = false, customFrameRate = 10) => {
        ToggleAnimation(animationName, loop, customFrameRate);
      },
      getCurrentAnimationName: () => currentAnimationName.get(),
    }),
    [ToggleAnimation, currentAnimationName]
  );

  useEffect(() => {
    if (autoPlay) {
      ToggleAnimation(defaultAnimationName, inLoop, frameRate);
    }
  }, [ToggleAnimation, autoPlay, defaultAnimationName, frameRate, inLoop]);

  const animatedStyle = useAnimatedStyle(() => {
    const selectedFrames =
      animations[currentAnimationName.get()]?.map((index) => frames[index]) ??
      [];
    const frame = selectedFrames[Math.floor(frameIndex.get())];
    if (!frame) {
      return {};
    }

    const scaleX = width / frame.w;
    const scaleY = height / frame.h;
    const positionX = frame.x * scaleX;
    const positionY = frame.y * scaleY;

    return {
      width: spriteSheetWidth * scaleX,
      height: spriteSheetHeight * scaleY,
      transform: [{ translateX: -positionX }, { translateY: -positionY }],
    };
  });

  return (
    <View
      key={`animation-block-${width}-${height}`}
      style={[
        {
          width,
          height,
        },
        styles.container,
      ]}
    >
      <AnimatedImage source={source} style={animatedStyle} contentFit="cover" />
    </View>
  );
});

const styles = StyleSheet.create({
  container: {
    overflow: 'hidden',
  },
});

export default AnimatedSprite;
