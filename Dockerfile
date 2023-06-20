FROM gradle:7.6-jdk17-ubuntu:latest as builder
ARG JAR_FILE=build/libs/homepage-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]