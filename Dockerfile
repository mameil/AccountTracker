#!/bin/bash

# base 이미지 설정
FROM openjdk:8-jre-alpine

# jar 파일 위치를 변수로 설정
ARG JAR_FILE=build/libs/AccountBook-0.0.1-SNAPSHOT.jar

# 환경변수 설정
ENV CUSTOM_NAME default

# jar 파일을 컨테이너 내부에 복사
COPY ${JAR_FILE} KDAccountBook.jar

EXPOSE 10001

# 실행 명령어
CMD ["java", "-Dtest.customName=${CUSTOM_NAME}", "-jar", "KDAccountBook.jar"]