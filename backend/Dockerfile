# 1. JDK 이미지로 빌드 환경 설정
FROM gradle:7.6.0-jdk17 AS build
WORKDIR /app

# 2. build.gradle, settings.gradle, 그리고 소스 코드 복사
COPY build.gradle settings.gradle ./
COPY src ./src

# 3. Gradle을 사용하여 JAR 파일 빌드
RUN gradle bootJar

# 4. JDK 이미지로 런타임 환경 설정
FROM openjdk:17-jdk-slim
WORKDIR /app

# 5. 빌드한 JAR 파일을 복사
COPY --from=build /app/build/libs/fitapet.jar /app/fitapet.jar

# 6. 애플리케이션 포트 설정
EXPOSE 8080

# 7. 애플리케이션 실행 명령어
ENTRYPOINT ["java", "-jar", "/app/fitapet.jar"]
