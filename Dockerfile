FROM gradle:7.6-jdk17-alpine as builder

COPY build.gradle settings.gradle .
ARG JAR_FILE=./build/libs/homepage-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
docker build -t 